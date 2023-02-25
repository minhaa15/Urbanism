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
        //Variables to help keep track of id's of items adde
        int polygonCount = 0;
        
        //Figuring out height and width of the square
        int gridSquareWidth = (int)(this.width/this.squareSize);
        int gridSquareHeight = (int)(this.height/this.squareSize);
        
        //Nested for loop to traverse through Grid
        for(double xPosition = 0; xPosition < gridSquareWidth; xPosition++){
            for(double yPosition = 0; yPosition < gridSquareHeight; yPosition++){
                
                //Calculates where the top left corner of this particular square in the grid will be
                double actualX = xPosition * squareSize;
                double actualY = yPosition * squareSize;
                
                //Create a new polgon
                MyPolygon poly = new MyPolygon(polygonCount);
                poly.generateRandomColor();
                this.addPolygon(poly);
                polygonCount++;
                
            }//end for loop for columns
        }//end for loop for rows
    }//end method generate
}//end of class gridMesh