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

    public int getNumberOfPolygons(){
        return this.numberOfPolygons;
    }

    @Override
    public void generate() {
        
        int vertexIdCounter = 0;
        int newSegmentCount = 0;     
        int polygonCount = 0;

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
        //applies Delaunay triangulation 
        DelaunayTriangulationBuilder dtb = new DelaunayTriangulationBuilder();
        dtb.setSites(newCoors);
        Geometry dtbOutput = dtb.getTriangles(new GeometryFactory(pm));

        System.out.println("NUMBER OF TRIANGLES" + dtbOutput.getNumGeometries());

        Map<Integer, List<Integer>> neighborMap = new HashMap<>();//hash map that stores ids of neighbour polygons
        
        for (int i = 0; i < setOfPolygons.size(); i++) {
            Polygon voronoiPolygon = setOfPolygons.get(i);
        
            List<Integer> neighbors = new ArrayList<>();

            for (int j = 0; j < dtbOutput.getNumGeometries(); j++) {
                Geometry triangle = dtbOutput.getGeometryN(j);
                
                Coordinate [] thisTriangleCoordinates = triangle.getCoordinates();

                // System.out.println(Arrays.toString(thisTriangleCoordinates));

                int target = 0; 
                
                boolean run = true;

                for(Coordinate c : thisTriangleCoordinates){
                    if(Double.compare(c.getX(), voronoiPolygon.getCentroid().getX())==0 && Double.compare(c.getY(), voronoiPolygon.getCentroid().getY())==0){
                        
                        if(target == 0){
                            System.out.println("ID: 0");
                            List <Integer> ids = getIds(setOfPolygons, thisTriangleCoordinates[1], thisTriangleCoordinates[2], i);
                            
                            for(int id : ids){
                                neighbors.add(id);
                            }
                        }else if(target == 1){
                            System.out.println("ID: 1");
                            List <Integer> ids = getIds(setOfPolygons, thisTriangleCoordinates[0], thisTriangleCoordinates[2], i);
                            for(int id : ids){
                                neighbors.add(id);
                            }    
                        }else{
                            System.out.println("ID: 2");
                            List <Integer> ids = getIds(setOfPolygons, thisTriangleCoordinates[0], thisTriangleCoordinates[1], i);
                            for(int id : ids){
                                neighbors.add(id);
                            }    
                        }
                        run = false;
                        break;
                    } 
                    

                    target++;
                    if(!run)break;
                }

                neighborMap.put(i,neighbors);
            }
        }

       

        int counter = 0; 
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
        Geometry output;

        VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
        
        vdb.setSites(coors);

        output = vdb.getDiagram(new GeometryFactory(new PrecisionModel(100)));

        Envelope env = new Envelope(0, this.width, 0, this.height);
        Geometry cropped = output.intersection(new GeometryFactory().toGeometry(env));

        return cropped;
    }

    public List <Coordinate> getCentroidsCoors(Geometry g, List<Coordinate>originalCoors){
        //Geometry output = g;
        List <Coordinate> coors = new ArrayList<>();
        
        List<Polygon> ps = new ArrayList<>();
        for (int i = 0; i < g.getNumGeometries(); i++) {
            Geometry geo = g.getGeometryN(i);
            if (geo instanceof Polygon) {
                ps.add((Polygon) geo);
                //System.out.println("the number of polygons");
            }
        }
        
        for(Polygon p : ps){
            //for(int j = 0; j < numberOfPolygons; j++){
            Coordinate c = new Coordinate();
            c.setX(p.getCentroid().getX());
            c.setY(p.getCentroid().getY());
            coors.add(c);
            //}//end for loop
        }
        return coors;
    }
    


    public List<Integer> getIds(List <Polygon> ps, Coordinate c1, Coordinate c2, int target){
        List <Integer> ids = new ArrayList<>();
        Polygon targetPolygon = ps.get(target);
        Coordinate [] targetPolygonVertices = targetPolygon.getCoordinates();

        System.out.println("PRANAV TARGET: " + Arrays.toString(targetPolygonVertices));

        int polyIndex = 0;

        for(Polygon p : ps){
            if(Double.compare(p.getCentroid().getX(), c1.getX())==0 && Double.compare(p.getCentroid().getY(), c1.getY())==0){
                ids.add(polyIndex);
            }

            polyIndex++;
        }

        polyIndex = 0;

        for(Polygon p : ps){
            if(Double.compare(p.getCentroid().getX(), c2.getX())==0 && Double.compare(p.getCentroid().getY(), c2.getY())==0){
                ids.add(polyIndex);
            }

            polyIndex++;
        }

        int counter = 0;

        for(int x = 0; x < ids.size(); x++){
            System.out.println(ids.get(x));
            Polygon test = ps.get(ids.get(x));
            Coordinate [] testCoors = test.getCoordinates();

            boolean b = false;

            for(int i = 0; i < targetPolygonVertices.length-1; i++){
                for(int j = 0; j < testCoors.length-1; j++){
                    if((targetPolygonVertices[i].equals2D(testCoors[j], 0.01)) && (targetPolygonVertices[i+1].equals2D(testCoors[j+1], 0.01))||(targetPolygonVertices[i].equals2D(testCoors[j+1], 0.01)) && (targetPolygonVertices[i+1].equals2D(testCoors[j], 0.01))){
                        // System.out.println("RAN AGAIN");

                        // System.out.println("TRIANGLES: " + Arrays.toString(testCoors));
                        b = true;
                    }   
                }
            } 
            
            if((targetPolygonVertices[0].equals2D(testCoors[0], 0.01)) && (targetPolygonVertices[targetPolygonVertices.length - 1].equals2D(testCoors[testCoors.length - 1], 0.01))&&(targetPolygonVertices[0].equals2D(testCoors[testCoors.length - 1], 0.01)) && (targetPolygonVertices[targetPolygonVertices.length - 1].equals2D(testCoors[0], 0.01))){
                // System.out.println("RAN AGAIN");
                b = true;
                System.out.println("EDGE THAT MATCHES: (" + targetPolygonVertices[0] + " " + targetPolygonVertices[targetPolygonVertices.length - 1]);
                System.out.println("EDGE THAT MATCHES: (" + testCoors[0] + " " + testCoors[testCoors.length - 1]);

            }   

            if(!b){
                System.out.println("RAN: " + Arrays.toString(test.getCoordinates()));
                ids.remove(counter);
            }

            counter++;
        }

        return ids;
    }   
}