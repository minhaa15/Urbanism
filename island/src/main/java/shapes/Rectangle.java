package shapes;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MyVertex;

public class Rectangle extends Shape{
    private double width;
    private double height;
    private double topLeftX;
    private double topLeftY;

    public Rectangle(double width, double height, double centerX, double centerY){
        this.width = width;
        this.height = height;
        this.topLeftX = centerX-this.width/2;
        this.topLeftY = centerY-this.height/2;

    }

    public int inside(List <MyVertex> vs){
        boolean in = false, out = false;
        for(MyVertex v : vs){
            if(v.getX() >= this.topLeftX && v.getX() <= this.topLeftX + this.width
                    && v.getY() >= this.topLeftY && v.getY() <= this.topLeftY + this.height){
                in = true;

            }else{
                out = true;
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