package customMesh;

import java.util.ArrayList;
import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public abstract class MeshADT {
    protected int presicion;
    protected List<MyVertex> vertices;
    protected List<MySegment> segments;
    protected List<MyPolygon> polygons;

    public void addPolygon(MyPolygon mp){
        polygons.add(mp);
    }

    public void addSegment(MySegment ms){
        segments.add(ms);
    }

    public void addVertex(MyVertex mv){
        vertices.add(mv);
    }

    public List<MyVertex> getVertexList(){
        List <MyVertex> vertexCopy = new ArrayList<>();

        for(MyVertex mv : this.vertices){
            vertexCopy.add(mv);
        }
        return vertexCopy;
    }//end of getVertexList

    public List<MySegment> getSegmentList(){
        List <MySegment> segmentsCopy = new ArrayList<>();

        for(MySegment ms : this.segments){
            segmentsCopy.add(ms);
        }
        return segmentsCopy;
    }

    public List<MyPolygon> getPolygonList(){
        List <MyPolygon> polygonCopy = new ArrayList<>();
        for(MyPolygon mp : this.polygons){
            polygonCopy.add(mp);
        }
        return polygonCopy;
    }

    public Mesh compile(){
        //Create appropirate lists for Structs objects
        List<Vertex>v = new ArrayList<>();
        List<Segment>s = new ArrayList<>();
        List<Polygon> p = new ArrayList<>();
    
        //converts lists of vertices in MyVertex Object to Structs.Vertex
        for(MyVertex mv : vertices){
            System.out.println(mv.getId());
            v.add(mv.compile());
        }
    
        //converts lists of segments in MyVertex Object to Structs.Segments
        for(MySegment ms : segments){
            s.add(ms.compile());
        }
    
        //converts lists of segments in MyPolygon Object to Structs.Polygon
        for(MyPolygon mp : polygons){
            p.add(mp.compile());
        }
    
        //return type Structs.Mesh which represents any MeshADT as Structs.Mesh
        return Mesh.newBuilder().addAllPolygons(p).addAllVertices(v).addAllSegments(s).build();
    }

    abstract public void generate();
}//end of abstract class MeshAdt