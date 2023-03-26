package converter;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MySegment;
import meshcomponents.MyVertex;

public class Extractor {
    private Mesh aMesh;

    public Extractor(Mesh aMesh){
        this.aMesh = aMesh;
    }

    public MyMesh convert(){
        MyMesh newMesh = new MyMesh();

        // newMesh.setSegments(aMesh.getSegmentsList());
        // newMesh.setVertexs(aMesh.getVerticesList());

        int i = 0;

        List <MyVertex> listOfVertices = new ArrayList<>();

        for(Vertex v : aMesh.getVerticesList()){
            listOfVertices.add(convertVertex(v, i));

            i++;
        }

        newMesh.setVertexs(listOfVertices);

        i = 0;

        List <MySegment> listOfSegments = new ArrayList<>();

        for(Segment s : aMesh.getSegmentsList()){
            listOfSegments.add(convertSegment(s, i, newMesh.getVertexs().get(s.getV1Idx()),  newMesh.getVertexs().get(s.getV2Idx())));

            i++;
        }

        newMesh.setSegments(listOfSegments);

        List <Polygon> polygons = this.aMesh.getPolygonsList();


        i = 0;

        double minX = 99999999, maxX = -99999999;
        double minY = 99999999, maxY = -99999999;

        for(Polygon p : polygons){
            newMesh.addPolygon(convertPolygon(p, i));

            if((minX > aMesh.getVerticesList().get(p.getCentroidIdx()).getX()) && (minY > aMesh.getVerticesList().get(p.getCentroidIdx()).getY())){
                minX = aMesh.getVerticesList().get(p.getCentroidIdx()).getX();
                minY = aMesh.getVerticesList().get(p.getCentroidIdx()).getY();
            }else if((maxX < aMesh.getVerticesList().get(p.getCentroidIdx()).getX())&&(maxY < aMesh.getVerticesList().get(p.getCentroidIdx()).getY())){
                maxX = aMesh.getVerticesList().get(p.getCentroidIdx()).getX();
                maxY = aMesh.getVerticesList().get(p.getCentroidIdx()).getY();
            }

            i++;
        }

        newMesh.setApproxCenterX((minX + maxX)/2.0);
        newMesh.setApproxCenterY((minY + maxY)/2.0);

        return newMesh;
    }

    private MyPolygon convertPolygon(Polygon p, int id) {
        MyPolygon polygon = new MyPolygon();

        polygon.setId(id);

        List <Integer> neighbours = p.getNeighborIdxsList();
        polygon.setNeighbours(neighbours);

        polygon.setCentroidId(p.getCentroidIdx());

        polygon.setSegments(p.getSegmentIdxsList());

        return polygon;
    }

    private MyVertex convertVertex(Vertex v, int id){
        MyVertex mv = new MyVertex();

        mv.setId(id);

        mv.setX(v.getX());
        mv.setY(v.getY());

        return mv;
    }

    private MySegment convertSegment(Segment v, int id, MyVertex v1, MyVertex v2){
        MySegment ms = new MySegment();

        ms.setId(id);

        ms.setV1(v1);
        ms.setV2(v2);

        return ms;
    }
}
