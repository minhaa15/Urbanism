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

                addVertices(poly, actualX, actualY);
                
                poly.generateRandomColor();
                this.addPolygon(poly);
                polygonCount++;
                
            }//end for loop for columns
        }//end for loop for rows
    }//end method generate

     public void addVertices(MyPolygon poly, int actualX, int actualY){
        int vertexCount = 0;

        //This array is simply the order in which I want to add verticies to a square
        int [] desiredXTraversal = {0,0,1,1};
        int [] desiredYTraversal = {0,1,1,0};

        //handle new verteces (1 for each part of the path)
        for(int count = 0; count < desiredXTraversal.length; count++){
            //These keep track of where we are in the desired path of adding vertices
            int i = desiredXTraversal[count];
            int j = desiredYTraversal[count];

            //New Vertex with the id vertexCount 
            MyVertex vertex = new MyVertex(vertexCount, this.presicion);
            
            //Set the vertex attributes
            vertex.setXPosition(actualX + (i*squareSize));
            vertex.setYPosition(actualY + (j*squareSize));
            vertex.generateRandomColor();

            //Checks if a vertex exists at this position
            MyVertex exists = (Functions.exists(this.vertices, vertex));

            //Decides whether to add a new vertex to the mesh
            if(exists == null){
                this.addVertex(vertex);
                poly.addVertexs(vertex);
                vertexCount++;
            }else{
                poly.addVertexs(exists);
            }
        }//end outer loop

        //centroid
        MyVertex vertex = new MyVertex(vertexCount, this.presicion, this.vertexThickness);

        //centroid x position
        double xSum = 0;
        double ySum = 0;

        //sum all coordinate
        for(MyVertex mv : poly.getVertexs()){
            xSum += mv.getXPosition();
            ySum += mv.getYPosition();
        }

        //average coordinates
        xSum/=poly.getVertexs().size();
        ySum/=poly.getVertexs().size();

        //set centroid positions
        vertex.setXPosition(xSum);
        vertex.setYPosition(ySum);
        vertex.generateRandomColor();

        //check if centroid has been created
        MyVertex checkExists = (Functions.exists(this.vertices, vertex));

        if(checkExists == null){
            this.addVertex(vertex);
            poly.setCentroidIndex(vertex.getId());
            vertexCount++;
        }else{
            poly.setCentroidIndex(checkExists.getId());
        }
    }
}//end of class gridMesh