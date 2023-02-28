package customMesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

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


    @Override
    public void generate() {
        PrecisionModel pm = new PrecisionModel(Math.pow(10, (this.presicion)));
        //creates the initial coordinates of the points on the canvas
        List <Coordinate> coors = createInitialPoints();

        //creates the oronoi diagram of the initial points created
        Geometry o1 = createVoronoi(coors);

        //applies the loyd relaxation for the number of times specified in DotGen
        List <Coordinate> newCoors = new ArrayList<>(getCentroidsCoors(o1, coors));
        for (int i=0;i<this.lloydRelaxation;i++){
            // System.out.printf("%d\n", i);
            o1 = createVoronoi(newCoors);
            newCoors = new ArrayList<>(getCentroidsCoors(o1, coors));
        }
        List <Coordinate> centroidList = new ArrayList<>();
        List <Polygon> setOfPolygons = new ArrayList<>();

        //creates a list of polygons based on the final voronoi diagram
        for (int i = 0; i < o1.getNumGeometries(); i++) {
            Geometry geo = o1.getGeometryN(i);
            if (geo instanceof Polygon) {
                Polygon p = (Polygon) geo;

                p = (Polygon)(new ConvexHull(p.getCoordinates(), new GeometryFactory(pm)).getConvexHull());

                setOfPolygons.add(p);
                centroidList.add(new Coordinate(((Polygon) geo).getCentroid().getX(), ((Polygon) geo).getCentroid().getY()));
            }
        }
    }
    private List <Coordinate> createInitialPoints(){
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

    private Geometry createVoronoi(List <Coordinate> coors){
        Geometry output;

        VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
        
        vdb.setSites(coors);

        output = vdb.getDiagram(new GeometryFactory(new PrecisionModel(100)));

        Envelope env = new Envelope(0, this.width, 0, this.height);
        Geometry cropped = output.intersection(new GeometryFactory().toGeometry(env));
        
        return cropped;
    }
    
    private List <Coordinate> getCentroidsCoors(Geometry g, List<Coordinate>originalCoors){
        List <Coordinate> coors = new ArrayList<>();

        List<Polygon> ps = new ArrayList<>();
        for (int i = 0; i < g.getNumGeometries(); i++) {
            Geometry geo = g.getGeometryN(i);
            if (geo instanceof Polygon) {
                ps.add((Polygon) geo);
            }
        }
        for(Polygon p : ps){
            Coordinate c = new Coordinate();
            c.setX(p.getCentroid().getX());
            c.setY(p.getCentroid().getY());
            coors.add(c);
            //}//end for loop
        }
        return coors;
    }
    private void createPolygons(List <Polygon> setOfPolygons,Map<Integer, List<Integer>> neighborMap, Geometry dtbOutput){
        int vertexIdCounter = 0;
        int newSegmentCount = 0;
        int polygonCount = 0;

        for(Polygon p : setOfPolygons){
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

            for(int neighbor : neighborMap.get(poly.getId())){
                poly.addNeighbor(neighbor);
            }

            this.addPolygon(poly);
            polygonCount++;
        }
    }
}