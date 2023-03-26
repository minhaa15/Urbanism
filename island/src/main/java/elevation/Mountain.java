package elevation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MySegment;
import meshcomponents.MyVertex;

public class Mountain extends Elevation{
    private double centreX;
    private double centreY;
    private int layers;
    private double radius;
    private double maxElevation;

    public Mountain(double centreX, double centreY, int layers, double maxElevation){
        this.centreX = centreX;
        this.centreY = centreY;
        this.layers = layers;
        this.maxElevation = maxElevation;

        //set intial radius 100 as we want that much increatment of layers
        this.radius = 50;
    }

    public void createElevation(MyMesh mesh){
        for(int i = 0; i < layers; i++){
            for(MyPolygon mp : mesh.getPolygons()){
                if(!(mp.getColor().equals(IslandColors.LAND))){continue;}
                List <MyVertex> vs = new ArrayList<>();

                for(int segment : mp.getSegments()){
                    MySegment seg = mesh.getSegments().get(segment);

                    vs.add(seg.getV1());
                    vs.add(seg.getV2());
                }

                boolean result = inside(vs);

                if(result){
                    mp.setElevation(maxElevation);

                    for(int x : mp.getSegments()){
                        mesh.getSegments().get(x).setElevation(maxElevation);
                    }

                    for(MyVertex mv : vs){
                        mv.setElevation(maxElevation);
                    }
                    // mp.setColor(new Color((i * 50), (i * 50), (i * 50)));
                }
            }

            this.maxElevation -= (this.maxElevation/this.layers);
            this.radius += 50;
        }
    }

    private boolean inside(List <MyVertex> vs){
        boolean in = false, out = false;
        for(MyVertex v : vs){
            double radius = findRadius(v.getX(), v.getY(), this.centreX, this.centreY);

            if(radius > this.radius){
                out = true;
            }

            if(radius < this.radius){
                in = true;
            }
        }

        if((in && out)){
            return true;
        }else{
            return false;
        }
    }

    private double findRadius(double x, double y, double centreX, double centreY){
        return Math.sqrt(Math.pow((x-centreX), 2) + Math.pow((y-centreY), 2));
    }
}
