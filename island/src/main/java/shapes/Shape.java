package shapes;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MySegment;
import meshcomponents.MyVertex;

public abstract class Shape {
    Color insideColor = null;
    Color borderColor = null;
    Color outsideColor = null;
    double centerX;
    double centerY;

    public void draw(MyMesh mesh){
        for(MyPolygon mp : mesh.getPolygons()){
            List <MyVertex> vs = new ArrayList<>();

            for(int segment : mp.getSegments()){
                MySegment seg = mesh.getSegments().get(segment);

                vs.add(seg.getV1());
                vs.add(seg.getV2());
            }

            int result = inside(vs);

            if(result == 0 && this.borderColor != null){
                mp.setColor(this.borderColor );
            }else if(result == 1 && this.outsideColor != null){
                mp.setColor(this.outsideColor);
            }else if(result == -1 && this.insideColor != null){
                mp.setColor(this.insideColor);
            }
        }
    }

    public Color getInsideColor() {
        return this.insideColor;
    }

    public void setInsideColor(Color insideColor) {
        this.insideColor = insideColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getOutsideColor() {
        return this.outsideColor;
    }

    public void setOutsideColor(Color outsideColor) {
        this.outsideColor = outsideColor;
    }

    public double getCenterX() {
        return this.centerX;
    }

    public double getCenterY() {
        return this.centerY;
    }

    public abstract int inside(List <MyVertex> vs);
    
}
