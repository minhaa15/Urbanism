package rivers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MySegment;
import meshcomponents.MyVertex;

public class River {

    public void createRiver(MyMesh mesh, int numberOfRivers, int seed){
        List <MyVertex> vs = new ArrayList<>();

        List <MyPolygon> polygons = mesh.getPolygons();
        List <MySegment> segments = mesh.getSegments();

        for(MyPolygon p : polygons){
            if(p.getColor().equals(IslandColors.LAND)){
                for(int i : p.getSegments()){
                    MySegment ms = segments.get(i);

                    vs.add(ms.getV1());
                    vs.add(ms.getV2());
                }
            }
        }
        
        List <Integer> random = pickPoint(vs.size(), seed, numberOfRivers);

        for(int r : random){
            vs.get(r).setColor(IslandColors.RIVER);

            Riverflow rf = new Riverflow();

            rf.createRiverFlow(vs.get(r), mesh);
        }        
    }

    private List<Integer> pickPoint(int n, int seed, int numberOfPoints){
        List <Integer> point = new ArrayList<>();

        Random bag = new Random();

        if(seed != -1){
            bag.setSeed(seed);
        }

        for(int i = 0; i < numberOfPoints; i++){
            point.add(bag.nextInt(n));
        }

        return point;
    }
}
