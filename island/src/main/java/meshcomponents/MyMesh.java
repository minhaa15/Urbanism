package meshcomponents;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class MyMesh {
    private List <MyPolygon> polygons;
    private List <MySegment> segments;
    private List <MyVertex> vertexs;
    private double approxCenterX;
    private double approxCenterY;

    public MyMesh (){
        polygons = new ArrayList<>();
    }

    public void setApproxCenterX(double approxCenterX){
        this.approxCenterX = approxCenterX;
    }

    public void setApproxCenterY(double approxCenterY){
        this.approxCenterY = approxCenterY;
    }

    public double getApproxCenterX(){
        return this.approxCenterX;
    }

    public double getApproxCenterY(){
        return this.approxCenterY;
    }

    public void addPolygon(MyPolygon p){
        polygons.add(p);
    }

    public List<MyPolygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<MyPolygon> polygons) {
        this.polygons = polygons;
    }

    public void setVertexs(List <MyVertex> vertexs){
        this.vertexs = vertexs;
    }

    public List <MyVertex> getVertexs(){
        return this.vertexs;
    }

    public void setSegments(List <MySegment> segments){
        this.segments = segments;
    }

    public List <MySegment> getSegments(){
        return this.segments;
    }
}
