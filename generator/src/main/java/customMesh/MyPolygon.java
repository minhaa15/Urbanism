package customMesh;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Polygon;
import java.awt.Color;
public class MyPolygon { 
  
    private List <MySegment> segments;
    private List <MyVertex> vertexs;
    private List <Integer> neighbors;
    private int centroidIndex;
    private Color color;
    private int id;
  
    public MyPolygon(int id){
      
        this.id = id;
        segments = new ArrayList<>();
        vertexs = new ArrayList<>();
        neighbors = new ArrayList<>();
    }
  
    public void setCentroidIndex(int centroidIndex){
        this.centroidIndex = centroidIndex;
    }
  
    public int getCentroidIndex(){
        return this.centroidIndex;
    }
  
    public void addNeighbor(int mp){
        neighbors.add(mp);
    }
  
    public List <Integer> getNeighbours(){
        List <Integer> neigborsCopy = new ArrayList<>();
        for(Integer mp : this.neighbors){
            neigborsCopy.add(mp);
        }
        return neigborsCopy;
    }//end method getVertexs

    public void addVertexs(MyVertex mv){
        vertexs.add(mv);
    }
  
    public List <MyVertex> getVertexs(){
        List <MyVertex> vertexsCopy = new ArrayList<>();
        for(MyVertex mv : this.vertexs){
            vertexsCopy.add(mv);
        }
        return vertexsCopy;
    }//end method getVertexs
  
    public void addSegments(MySegment ms){
        segments.add(ms);
    }
  
    public List <MySegment> getSegments(){
        List <MySegment> segmentsCopy = new ArrayList<>();
        for(MySegment ms : this.segments){
            segmentsCopy.add(ms);
        }
        return segmentsCopy;
    }
  
    public int getId() {
        return this.id;
    }
  
    public Polygon compile(){
        List<Integer> segs = new ArrayList<>();
        for(MySegment ms : this.segments){
            segs.add(ms.getId());
        }//end for loop
        return 
          Polygon.newBuilder().addAllSegmentIdxs(segs).setCentroidIdx(centroidIndex).addAllNeighborIdxs(neighbors).build(); 
    }//end of compile
  
    public void generateRandomColor(){
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);      
        color = new Color(red, blue, green);
    }//end of generateRandomColor
  
    public Color getColor(){
        return this.color;
    }//end of getColor
  
    public void setColor(Color c){
        this.color = new Color(c.getRed(), c.getGreen(), c.getBlue());
    }//end setColor
  
}//end of class Polygon
