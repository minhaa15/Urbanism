package customMesh;
import java.util.Random;
import java.awt.Color;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class MySegment {  
    private MyVertex vertex1; 
    private MyVertex vertex2;  
    private Color color; 
    private int id;
    private float thickness;

    public MySegment(int id, float thickness){
        this.id = id;
        this.thickness = thickness;

    }//end of constructor

    public int getId() {
        return this.id;
    }//end of getId

    public MyVertex getVertex1() {
        return this.vertex1;
    }

    public void setVertex1(MyVertex vertex1) {
        this.vertex1 = vertex1;
    }

    public MyVertex getVertex2() {
        return this.vertex2;
    }

    public void setVertex2(MyVertex vertex2) {
        this.vertex2 = vertex2;
    }

    public float getThickness(){
        return this.thickness;
    }

    public void setThickness(float thickness){
        this.thickness = thickness;
    }
 
    public Segment compile(){
        String colorCode = Functions.extractColor(this.color);
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        Property lineThickness = Property.newBuilder().setKey("lineThickness").setValue(String.valueOf(thickness)).build();
        return Segment.newBuilder().setV1Idx(this.vertex1.getId()).setV2Idx(this.vertex2.getId()).addProperties(color).addProperties(lineThickness).build(); 
    }//end Compile

    public void generateColor(){
        int red = (int)((this.vertex1.getColor().getRed() + this.vertex2.getColor().getRed())/2);
        int green = (int)((this.vertex1.getColor().getGreen() + this.vertex2.getColor().getGreen())/2);
        int blue = (int)((this.vertex1.getColor().getBlue() + this.vertex2.getColor().getBlue())/2);
        int alpha = (int)((this.vertex1.getColor().getAlpha() + this.vertex2.getColor().getAlpha())/2);

        color = new Color(red, blue, green, alpha);
    }//end of generateRandomColor

    public Color getColor(){
        return this.color;
    }//end of getColor

    public void setColor(Color c){
        this.color = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }//end setColor



    public boolean equals(MySegment ms) {
        
        boolean eq = false;

        if((this.getVertex1().equals(ms.getVertex1()))&&(this.getVertex2().equals(ms.getVertex2()))){
            eq = true;
        }

        if((this.getVertex1().equals(ms.getVertex2()))&&(this.getVertex2().equals(ms.getVertex1()))){
            eq = true;
        }

        return eq;
    }
    
}//end Segment