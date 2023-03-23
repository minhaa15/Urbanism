package meshcomponents;
import java.util.List;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import java.awt.Color;

public class MyPolygon {
    private List <Integer> neighbours;
    private Color color; 
    private int id; 
    private List <Integer> segments;
    private int centroidId;

    public MyPolygon (){

    }

    public void setCentroidId(int centroidId){
        this.centroidId = centroidId;
    }

    public int getCentroidId(){
        return this.centroidId;
    }

    public List<Integer> getNeighbours() {
        return this.neighbours;
    }

    public void setNeighbours(List<Integer> neighbours) {
        this.neighbours = neighbours;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getSegments() {
        return this.segments;
    }

    public void setSegments(List<Integer> segments) {
        this.segments = segments;
    }

}