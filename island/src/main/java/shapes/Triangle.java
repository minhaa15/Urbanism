package shapes;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MyVertex;

public class Triangle extends Shape {
    private double sideLength;

    public Triangle(double sideLength, double centerX, double centerY) {
        this.sideLength = sideLength;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public int inside(List<MyVertex> vs){
        boolean in = false, out = false;
        double halfSide = sideLength / 2;

        double middleBase = this.centerX;
        double middleBaseY = this.centerY+halfSide;

        for(MyVertex v : vs){

            double expectedValY = expectedY(v.getX());

            if (expectedValY-halfSide < v.getY() && middleBaseY > v.getY() &&
                    v.getX() > (this.centerX-halfSide) && v.getX() < (this.centerX+halfSide)){
                in = true;
            } else{
                out = true;
            }
        }
        if((in && out)){
            return 0;
        } else if(in){
            return -1;
        } else {
            return 1;
        }
    }
    private double expectedY(double x) {
        double y =0;

        double height = (this.sideLength)/2 * Math.sin(Math.toRadians(60));
        if (x <= this.centerX){
            double b = -(this.centerX+(this.sideLength/2)) * rightSlope();
            y = (rightSlope()*x) + b;

        }else if (x > this.centerX){
            double b = (this.centerX-(this.sideLength/2)) * rightSlope();
            y = (leftSlope()*x) + b;
        }


        return y;
    }


    private double rightSlope(){
        double height = this.sideLength * Math.sin(Math.toRadians(60));
        double slope = (-height/(this.sideLength/2));
        return slope;
    }
    private double leftSlope(){
        double height = this.sideLength * Math.sin(Math.toRadians(60));
        double slope = (height/(this.sideLength/2));
        return slope;
    }
}