package converter;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;

public class Compiler {
    public Mesh compile(MyMesh m){
        List <Polygon> polys = new ArrayList<>();

        for(MyPolygon p : m.getPolygons()){
            polys.add(compile(p));
        }//end for

        return Mesh.newBuilder().addAllPolygons(polys).addAllVertices(m.getVertexs()).addAllSegments(m.getSegments()).build();
    }//end of method compile

    private Polygon compile(MyPolygon mp){
        if(mp.getColor() != null){
            String colorCode = mp.getColor().getRed() + "," + mp.getColor().getGreen() + "," + mp.getColor().getBlue();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();    
            return Polygon.newBuilder().addAllSegmentIdxs(mp.getSegments()).setCentroidIdx(mp.getCentroidId()).addAllNeighborIdxs(mp.getNeighbours()).addProperties(color).build(); 
        }

        return Polygon.newBuilder().addAllSegmentIdxs(mp.getSegments()).setCentroidIdx(mp.getCentroidId()).addAllNeighborIdxs(mp.getNeighbours()).build(); 

    }//end of compile
}//end of class compiler