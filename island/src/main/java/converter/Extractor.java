package converter;
import java.awt.Color;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;

public class Extractor {
    private Mesh aMesh;

    public Extractor(Mesh aMesh){
        this.aMesh = aMesh;
    }    

    public MyMesh convert(){
        MyMesh newMesh = new MyMesh();

        newMesh.setSegments(aMesh.getSegmentsList());
        newMesh.setVertexs(aMesh.getVerticesList());

        List <Polygon> polygons = this.aMesh.getPolygonsList();

        int i = 0;

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
}
