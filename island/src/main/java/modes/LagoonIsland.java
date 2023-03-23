package modes;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;

public class LagoonIsland {
    private double radius1 = 400;
    private double radius2 = 200;

    public void generate(MyMesh mesh){
        boolean in1 = false, out1 = false;
        boolean in2 = false, out2 = false;

        System.out.println(mesh.getApproxCenterX() + ", " + mesh.getApproxCenterY());

        for(MyPolygon mp : mesh.getPolygons()){
            List <Vertex> vs = new ArrayList<>();

            for(int segment : mp.getSegments()){
                Segment seg = mesh.getSegments().get(segment);

                vs.add(mesh.getVertexs().get(seg.getV1Idx()));
                vs.add(mesh.getVertexs().get(seg.getV2Idx()));
            }

            for(Vertex v : vs){
                double radius = findRadius(v.getX(), v.getY(), mesh.getApproxCenterX(), mesh.getApproxCenterY());
                
                if(radius > this.radius1){
                    out1 = true;
                }

                if(radius < this.radius1){
                    in1 = true;
                }

                if(radius > this.radius2){
                    out2 = true;
                }

                if(radius < this.radius2){
                    in2 = true;
                }
            }

            if((in1 && out1) || (in2 && out2)){
                mp.setColor(new Color(255, 217, 150));
            }else if(in2){
                mp.setColor(new Color(174, 187, 252));
            }else if(in1){
                mp.setColor(new Color(40, 168, 17));
            }else if(out1){
                mp.setColor(new Color(92, 74, 255));
            }

            in1 = false;
            out1 = false;
            in2 = false;
            out2 = false;
        }
    }

    public double findRadius(double x, double y, double centreX, double centreY){
        return Math.sqrt(Math.pow((x-centreX), 2) + Math.pow((y-centreY), 2));
    }
}
