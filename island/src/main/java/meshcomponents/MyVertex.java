package meshcomponents;

import java.awt.Color;

public class MyVertex {
    private double x;
    private double y; 
    private Color color;
    private double elevation;
    private int citySize;
    private int id;

    private int next;

    public MyVertex(){
        this.elevation = 0;
        this.citySize = 0;
        this.next = -1;
    }

    public int getNext() {
        return next;
    }

    public void setNext(int next) {
        this.next = next;
    }

    public void setCitySize(int citySize) {
        this.citySize = citySize;
    }

    public int getCitySize() {
        return citySize;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }    

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getElevation() {
        return this.elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }    
}
