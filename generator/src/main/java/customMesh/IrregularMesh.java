package customMesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequenceFactory;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

public class IrregularMesh extends MeshADT{
    private int numberOfPolygons;
    private double height; 
    private double width; 

    public IrregularMesh(int presicion, int numberOfPolygons, double height, double width){
        this.numberOfPolygons = numberOfPolygons;
        this.height = height;
        this.width = width;
        this.presicion = presicion;

        vertices = new ArrayList<>();
        segments = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public int getNumberOfPolygons(){
        return this.numberOfPolygons;
    }

    @Override
    public void generate() {
        Random bag = new Random();
        
        List <Coordinate> coors = new ArrayList<>();

        for(int i = 0; i < numberOfPolygons; i++){
            MyVertex vertex = new MyVertex(i, this.presicion);
            vertex.setXPosition(bag.nextDouble(0,width));
            vertex.setYPosition(bag.nextDouble(0,height));
            vertex.generateRandomColor();

            Coordinate c = new Coordinate();
            c.setX(vertex.getXPosition());
            c.setY(vertex.getYPosition());
            coors.add(c);

            this.addVertex(vertex);
        }//end for loop

        //generate Voronoi
        VoronoiDiagramBuilder vdb = new VoronoiDiagramBuilder();
        
        vdb.setSites(coors);

        Geometry output = vdb.getDiagram(new GeometryFactory());
        
        List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < output.getNumGeometries(); i++) {
            Geometry g = output.getGeometryN(i);
            if (g instanceof Polygon) {
                polygons.add((Polygon) g);
            }
        }

        int vertexIdCounter = 0, numberOfPolygons = 0;
        
        for(Polygon p : polygons){
            MyPolygon poly = new MyPolygon(numberOfPolygons);

            Coordinate [] thisPolygonCoordinates = p.getCoordinates();
        
            for(Coordinate c : thisPolygonCoordinates){
                //New Vertex with the id vertexCount 
                MyVertex vertex = new MyVertex(vertexIdCounter, this.presicion);
                
                //Set the vertex attributes
                vertex.setXPosition(Math.abs(c.getX()));
                vertex.setYPosition(Math.abs(c.getY()));
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

            int segmentCount = 0;
            LineString boundary = p.getExteriorRing();
            Coordinate[] coordinatesArray = boundary.getCoordinates();

            for (int i = 0; i < coordinatesArray.length - 1; i++) {
                Coordinate start = coordinatesArray[i];
                MyVertex mv1 = new MyVertex(vertexIdCounter, this.presicion);
                
                //Set the vertex attributes
                mv1.setXPosition(Math.abs(start.getX()));
                mv1.setYPosition(Math.abs(start.getY()));
                mv1.generateRandomColor();

                int id1 = Functions.findId(this.vertices, mv1);

                Coordinate end = coordinatesArray[i+1];

                MyVertex mv2 = new MyVertex(vertexIdCounter, this.presicion);
                
                //Set the vertex attributes
                mv2.setXPosition(Math.abs(end.getX()));
                mv2.setYPosition(Math.abs(end.getY()));
                mv2.generateRandomColor();

                int id2 = Functions.findId(this.vertices, mv2);

                //create last segment
                MySegment newSegment = new MySegment(segmentCount);
                newSegment.setVertex1(this.getVertexList().get(id1));
                newSegment.setVertex2(this.getVertexList().get(id2));
                newSegment.generateColor();

                MySegment exists = (Functions.exists(this.segments, newSegment));

                if(exists == null){
                    this.addSegment(newSegment);
                    poly.addSegments(newSegment);
                    segmentCount++;
                }else{
                    poly.addSegments(exists);
                }
            }

            this.addPolygon(poly);
        }
    }
}