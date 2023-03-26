package meshcomponents;

import java.awt.Color;

public class MySegment {
    private MyVertex v1;
    private MyVertex v2;
    private Color color;
    private double elevation;   
    private int id;
    private int weight;

    public MySegment(){
        this.elevation = 0;
        this.weight = 1;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MyVertex getV1() {
        return this.v1;
    }

    public void setV1(MyVertex v1) {
        this.v1 = v1;
    }

    public MyVertex getV2() {
        return this.v2;
    }

    public void setV2(MyVertex v2) {
        this.v2 = v2;
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


    public boolean equals(MySegment ms) {
        
        boolean eq = false;

        if((this.getV1().equals(ms.getV1()))&&(this.getV2().equals(ms.getV2()))){
            eq = true;
        }

        if((this.getV1().equals(ms.getV2()))&&(this.getV2().equals(ms.getV1()))){
            eq = true;
        }

        return eq;
    }
}
