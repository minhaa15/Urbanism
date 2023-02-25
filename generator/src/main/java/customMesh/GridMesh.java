package customMesh;

import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.List;

public class GridMesh extends MeshADT{
    private double width;
    private double height;
    private double squareSize;
  
    public GridMesh(double width, double height, double squareSize, int p){
        this.width = width;
        this.height = height;
        this.squareSize = squareSize;   
      
        vertices = new ArrayList<>();
        segments = new ArrayList<>();
        polygons = new ArrayList<>();
        presicion = p;
    }//end constructor
    @Override
    public void generate() {   

             
    }//end method generate
}//end of class gridMesh