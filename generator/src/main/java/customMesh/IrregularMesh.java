package customMesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import com.google.protobuf.CodedInputStream;

public class IrregularMesh extends MeshADT{
    private int numberOfPolygons;
    private double height; 
    private double width; 
    private float lineThickness;
    private float vertexThickness;
    private int lloydRelaxation;

    public IrregularMesh(int presicion, int numberOfPolygons, double height, double width, float vertexThickness, float lineThickness, int lloydRelaxation){
        this.numberOfPolygons = numberOfPolygons;
        this.height = height;
        this.width = width;
        this.presicion = presicion;
        this.vertexThickness = vertexThickness;
        this.lineThickness = lineThickness;
        this.lloydRelaxation = lloydRelaxation;

        vertices = new ArrayList<>();
        segments = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public int getNumberOfPolygons(){
        return this.numberOfPolygons;
    }

    @Override
    public void generate() {
        
        int vertexIdCounter = 0;
        int newSegmentCount = 0;     
        int polygonCount = 0;

        List <Coordinate> coors = createInitialPoints();

        Geometry o1 = createVoronoi(coors);
        List <Coordinate> coors2 = applyLloydRelaxation(o1);

        Geometry o2 = createVoronoi(coors2);
        // Create a clipping polygon with corners at (50,50) and (70,70)

        Envelope env = new Envelope(0, this.width, 0, this.height);

        Geometry cropped = o2.intersection(new GeometryFactory().toGeometry(env));

        // for(Coordinate c :  coors2){
        //     MyVertex mv = new MyVertex(vertexIdCounter, this.presicion, this.vertexThickness);
            
        //     if(c.getX() <= this.width && c.getX() >=0){
        //         if(c.getY() <= this.height && c.getY() >=0){
        //             mv.setXPosition(c.getX());
        //             mv.setYPosition(c.getY());
        //             mv.generateRandomColor();

        //             this.addVertex(mv);

        //             vertexIdCounter++;
        //         }
        //     }
        // }
        List <Coordinate> centroidList = new ArrayList<>();

        for (int i = 0; i < cropped.getNumGeometries(); i++) {
            Geometry geo = cropped.getGeometryN(i);
            if (geo instanceof Polygon) {
                centroidList.add(new Coordinate(((Polygon) geo).getCentroid().getX(), ((Polygon) geo).getCentroid().getY()));
            }
        }

        DelaunayTriangulationBuilder dtb = new DelaunayTriangulationBuilder();
        dtb.setSites(centroidList);
        Geometry dtbOutput = dtb.getTriangles(new GeometryFactory());

        Map<Polygon, List<Integer>> neighborMap = new HashMap<>();

        for (int i = 0; i < cropped.getNumGeometries(); i++) {
            Geometry voronoiPolygon = cropped.getGeometryN(i);

            if (voronoiPolygon instanceof Polygon) {
                List<Integer> neighbors = new ArrayList<>();

                for (int j = 0; j < dtbOutput.getNumGeometries(); j++) {
                    Geometry triangle = dtbOutput.getGeometryN(j);
                    
                    Coordinate [] thisTriangleCoordinates = triangle.getCoordinates();


                    for(Coordinate c : thisTriangleCoordinates){
                        for(int count = 0; count < cropped.getNumGeometries(); count++){
                            if(count == i)continue;
                            Coordinate xy = new Coordinate(cropped.getGeometryN(count).getCentroid().getX(), cropped.getGeometryN(count).getCentroid().getY());
                            
                            if(c.equals(xy))neighbors.add(count);
                        }
                    }//end for
                }//end for

                neighborMap.put((Polygon) voronoiPolygon, neighbors);
            }
        }

        for(Polygon p : neighborMap.keySet()){
            MyPolygon poly = new MyPolygon(polygonCount);

            Coordinate [] thisPolygonCoordinates = p.getCoordinates();
        
            for(Coordinate c : thisPolygonCoordinates){
                //New Vertex with the id vertexCount 
                MyVertex vertex = new MyVertex(vertexIdCounter, this.presicion, 20);
                
                //Set the vertex attributes
                vertex.setXPosition(c.getX());
                vertex.setYPosition(c.getY());
                vertex.generateRandomColor();

                //Checks if a vertex exists at this position
                MyVertex exists = (Functions.exists(this.vertices, vertex));

                //Decides whether to add a new vertex to the mesh
                if(exists == null){
                    this.addVertex(vertex);
                    poly.addVertexs(vertex);
                    vertexIdCounter++;
                }else{
                    poly.addVertexs(exists);
                }
            }

            //stores a list of the vertices associated with this polygon
            List <MyVertex> pVerts = poly.getVertexs();

            //Loop to create segments
            for(int i = 0; i < pVerts.size()-1; i++){
                //Create a segment
                MySegment newSegment = new MySegment(newSegmentCount, this.lineThickness);

                //set segment attributes
                newSegment.setVertex1(pVerts.get(i));
                newSegment.setVertex2(pVerts.get(i+1));
                newSegment.generateColor();
                
                //Check is segment exists
                MySegment exists = (Functions.exists(this.segments, newSegment));

                //Descide whether to add new segment to mesh
                if(exists == null){
                    this.addSegment(newSegment);
                    poly.addSegments(newSegment);
                    newSegmentCount++;
                }else{
                    poly.addSegments(exists);
                }
            }


            //New Vertex with the id vertexCount 
            MyVertex centroid = new MyVertex(vertexIdCounter, this.presicion, this.vertexThickness);
                            
            centroid.setXPosition(p.getCentroid().getX());
            centroid.setYPosition(p.getCentroid().getY());
            centroid.generateRandomColor();

            //Checks if a vertex exists at this position
            MyVertex exists = (Functions.exists(this.vertices, centroid));

            //Decides whether to add a new vertex to the mesh
            if(exists == null){
                this.addVertex(centroid);
                poly.setCentroidIndex(vertexIdCounter);
                vertexIdCounter++;
            }else{
                poly.setCentroidIndex(exists.getId());
            }

            for(int neighbor : neighborMap.get(p)){
                System.out.println("Here is a neighbor: " + neighbor);
                poly.addNeighbor(neighbor);
            }

            this.addPolygon(poly);
            polygonCount++;
        }
    }

    public List <Coordinate> createInitialPoints(){
        Random bag = new Random();
        List <Coordinate> coors = new ArrayList<>();

        for(int i = 0; i < this.numberOfPolygons; i++){
            Coordinate c = new Coordinate();
            c.setX(bag.nextDouble(0,width));
            c.setY(bag.nextDouble(0,height));
            coors.add(c);
        }

        return coors;
    }

    public Geometry createVoronoi(List <Coordinate> coors){
        Geometry output = null;

        VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
        
        vdb.setSites(coors);

        output = vdb.getDiagram(new GeometryFactory());

        return output;
    }

    public List<Coordinate> applyLloydRelaxation(Geometry g){
        VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();        
        Geometry output = g;
        List <Coordinate> coors = new ArrayList<>();

        List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < g.getNumGeometries(); i++) {
            Geometry geo = g.getGeometryN(i);
            if (geo instanceof Polygon) {
                polygons.add((Polygon) geo);
            }
        }

        for(int i = 0; i < this.lloydRelaxation; i++){
            for(Polygon p : polygons){
                for(int j = 0; j < numberOfPolygons; j++){
                    Coordinate c = new Coordinate();
                    c.setX(p.getCentroid().getX());
                    c.setY(p.getCentroid().getY());
                    coors.add(c);
                }//end for loop
            }
        
            vdb.setSites(coors);
    
            output = vdb.getDiagram(new GeometryFactory());

            polygons = new ArrayList<>();
            for (int x = 0; x < output.getNumGeometries(); x++) {
                Geometry geo = output.getGeometryN(x);
                if (geo instanceof Polygon) {
                    polygons.add((Polygon) geo);
                }
            }
        }
        
        return coors;
    }

    public List<Point> getCentroids(List <Polygon> polygons){
        List<Point> centroids = new ArrayList<>();

        for(Polygon p : polygons){
            centroids.add(p.getCentroid());
        }

        return centroids;
    }
}