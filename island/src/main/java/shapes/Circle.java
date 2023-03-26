package shapes;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MyVertex;

public class Circle extends Shape{
    private double radius; 

    public Circle(double radius, double centerX, double centerY){
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    private double findRadius(double x, double y, double centreX, double centreY){
        return Math.sqrt(Math.pow((x-centreX), 2) + Math.pow((y-centreY), 2));
    }

    public int inside(List <MyVertex> vs){
        boolean in = false, out = false;
        for(MyVertex v : vs){
            double radius = findRadius(v.getX(), v.getY(), this.centerX, this.centerY);
            
            if(radius > this.radius){
                out = true;
            }

            if(radius < this.radius){
                in = true;
            }
        }

        if((in && out)){
            return 0;
        }else if(in){
            return -1;
        }else{
            return 1; 
        }
    }
}
