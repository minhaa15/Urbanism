package customMesh;
import java.util.Random;
import java.awt.Color;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;

public class MyVertex {

    private double xPosition;
    private double yPosition;
    private int id;
    private Color color;
    private int presicion;
    private float thickness;


    public MyVertex(int id, int presicion, float thickness){
        this.id = id;
        this.presicion = presicion;
        this.thickness = thickness;
    }//end of constructor


    public double getXPosition() {
        return this.xPosition;
    }//end of getXPosition

    public void setXPosition(double xPosition) {
        double roundNum = Math.round(xPosition * Math.pow(10,presicion)) /Math.pow(10,presicion);
        this.xPosition = roundNum;
    }//end of setXPosition

    public double getYPosition() {
        return this.yPosition;
    }//end of getYPosition

    public void setYPosition(double yPosition) {
        double roundNum = Math.round(yPosition * Math.pow(10,presicion)) /Math.pow(10,presicion);
        this.yPosition = roundNum;
    }//end of setYPosition

    public int getId() {
        return this.id;
    }//end getId

    public float getThickness(){
        return this.thickness;
    }

    public void setThickness(float thickness){
        this.thickness = thickness;
    }

    public void generateRandomColor(){
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        int alpha = bag.nextInt(255);

        color = new Color(red, blue, green, alpha);
    }//end of generateRandomColor

    public Color getColor(){
        return this.color;
    }//end of getColor

    public void setColor(Color c){
        this.color = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }//end setColor


    public Vertex compile(){
        String colorCode = Functions.extractColor(this.color);
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
        Property vertexThickness = Property.newBuilder().setKey("vertexThickness").setValue(String.valueOf(thickness)).build();
        return Vertex.newBuilder().setX(xPosition).setY(yPosition).addProperties(color).addProperties(vertexThickness).build();
    }//end compile

    public boolean equals(MyVertex mv){
        boolean eq = (this.xPosition == mv.xPosition)&&(this.yPosition == mv.yPosition);
        return eq;
    }
}//endVertex