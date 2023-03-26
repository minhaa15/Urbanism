package rivers;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MySegment;
import meshcomponents.MyVertex;

public class Riverflow {
    public void createRiverFlow(MyVertex start, MyMesh mesh){

        List <MySegment> segs = new ArrayList<>();

        for(MyPolygon mp : mesh.getPolygons()){
            if(mp.getColor().equals(IslandColors.LAND)){
                for(int i : mp.getSegments()){
                    segs.add(mesh.getSegments().get(i));
                }
            }
        }

        MyVertex current = start;
        MyVertex temp = current;

        List <MySegment> vistited = new ArrayList<>();
        List <MyVertex> visitedV = new ArrayList<>();

        boolean run = true;

        do{
            temp = current;            
            for(MySegment ms : segs){

                if(vistited.contains(ms)){
                    break;
                }if(visitedV.contains(ms.getV1()) && visitedV.contains(ms.getV2()))continue;
                // if(ms.getColor() != null)if(ms.getColor().equals(IslandColors.RIVER))continue;

                if(((ms.getV1().getX() == current.getX())&&(ms.getV1().getY() == current.getY())) || ((ms.getV2().getX() == current.getX())&&(ms.getV2().getY() == current.getY()))){
                    if(ms.getElevation() <= current.getElevation()){
                        vistited.add(ms);

                        visitedV.add(ms.getV1());
                        visitedV.add(ms.getV2());
                        
                        ms.setColor(IslandColors.RIVER);
                        ms.setWeight(ms.getWeight() + 5);


                    
                        if(ms.getV1() == current){
                            System.out.println("help");
                            current = ms.getV2();
                        }else{
                            System.out.println("help");
                            current = ms.getV1();
                        }
                    }
                }
            }
                System.out.println("TEMP: " + temp.getX() + " " + temp.getY());
                System.out.println("CURRENT: " + temp.getX() + " " + temp.getY());

                System.out.println(((temp.getX() != current.getX())&&(temp.getY() != current.getY())));
        
                if((((Double.compare(temp.getX(), current.getX()) == 0 )&&(Double.compare(temp.getY(), current.getY()) == 0)))){
                    System.out.println("BROKEND");
                    run = false;
                }    
        }while(run);

        System.out.println("REACHED");
    }
}
