package converter;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MySegment;
import meshcomponents.MyVertex;

public class Compiler {
    public Mesh compile(MyMesh m){
        List <Polygon> polys = new ArrayList<>();
        List <Vertex> vs = new ArrayList<>();
        List <Segment> segs = new ArrayList<>();

        for(MyPolygon p : m.getPolygons()){
            polys.add(compile(p));
        }//end for

        for(MySegment ms : m.getSegments()){
            segs.add(compile(ms));
        }

        for(MyVertex mv : m.getVertexs()){
            vs.add(compile(mv));
        }

        return Mesh.newBuilder().addAllPolygons(polys).addAllVertices(vs).addAllSegments(segs).build();
    }

    private Polygon compile(MyPolygon mp){
        if(mp.getColor() != null){
            String colorCode = mp.getColor().getRed() + "," + mp.getColor().getGreen() + "," + mp.getColor().getBlue();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            return Polygon.newBuilder().addAllSegmentIdxs(mp.getSegments()).setCentroidIdx(mp.getCentroidId()).addAllNeighborIdxs(mp.getNeighbours()).addProperties(color).build();
        }

        return Polygon.newBuilder().addAllSegmentIdxs(mp.getSegments()).setCentroidIdx(mp.getCentroidId()).addAllNeighborIdxs(mp.getNeighbours()).build();

    }

    public Segment compile(MySegment ms){
        String weight = String.valueOf(ms.getWeight());
        Property weigh = Property.newBuilder().setKey("weight").setValue(weight).build();


        if(ms.getColor() != null){
            String colorCode = ms.getColor().getRed() + "," + ms.getColor().getGreen() + "," + ms.getColor().getBlue();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            return Segment.newBuilder().setV1Idx(ms.getV1().getId()).setV2Idx(ms.getV2().getId()).addProperties(color).addProperties(weigh).build();
        }

        return Segment.newBuilder().setV1Idx(ms.getV1().getId()).setV2Idx(ms.getV2().getId()).addProperties(weigh).build();

    }

    public Vertex compile(MyVertex mv){
        Property citySize = Property.newBuilder().setKey("SizeOfCity").setValue(String.valueOf(mv.getCitySize())).build();
        Property nextNode = Property.newBuilder().setKey("next_node").setValue(String.valueOf(mv.getNext())).build();

        if(mv.getColor() != null){
            String colorCode = mv.getColor().getRed() + "," + mv.getColor().getGreen() + "," + mv.getColor().getBlue();
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();

            return Vertex.newBuilder().setX(mv.getX()).setY(mv.getY()).addProperties(citySize).addProperties(nextNode).addProperties(color).build();
        }

        return Vertex.newBuilder().setX(mv.getX()).setY(mv.getY()).addProperties(citySize).addProperties(nextNode).build();
    }
}
