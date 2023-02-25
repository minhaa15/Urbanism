package customMesh;

import java.beans.VetoableChangeListener;
import java.util.ArrayList;
import java.util.List;

public class GridMesh extends MeshADT{
    private double width;
    private double height;
    private double squareSize;
    private float lineThickness;
    private float vertexThickness;


    public GridMesh(double width, double height, double squareSize, int p, float vertexThickness, float lineThickness){
        this.width = width;
        this.height = height;
        this.squareSize = squareSize; 
        this.lineThickness = lineThickness;
        this.vertexThickness = vertexThickness;  
      
        vertices = new ArrayList<>();
        segments = new ArrayList<>();
        polygons = new ArrayList<>();
        presicion = p;
    }//end constructor
    @Override
    public void generate() {    
        //Variables to help keep track of id's of items adde
        int polygonCount = 0, vertexCount = 0, segmentCount = 0;
        
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

                vertexCount = addVertices(poly, actualX, actualY, vertexCount);
                segmentCount = addSegments(poly, segmentCount);
                createNeighbors(poly, gridSquareHeight, gridSquareWidth);

                poly.generateRandomColor();
                this.addPolygon(poly);
                polygonCount++;
                
            }//end for loop for columns
        }//end for loop for rows
    }//end method generate

    public int addVertices(MyPolygon poly, double actualX, double actualY, int vertexCount){
        int newVertexCount = vertexCount;

        //This array is simply the order in which I want to add verticies to a square
        int [] desiredXTraversal = {0,0,1,1};
        int [] desiredYTraversal = {0,1,1,0};

        //handle new verteces (1 for each part of the path)
        for(int count = 0; count < desiredXTraversal.length; count++){
            //These keep track of where we are in the desired path of adding vertices
            int i = desiredXTraversal[count];
            int j = desiredYTraversal[count];

            //New Vertex with the id vertexCount 
            MyVertex vertex = new MyVertex(newVertexCount, this.presicion, this.vertexThickness);
            
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
                newVertexCount++;
            }else{
                poly.addVertexs(exists);
            }
        }//end outer loop

        //centroid
        MyVertex vertex = new MyVertex(newVertexCount, this.presicion, this.vertexThickness);

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
            newVertexCount++;
        }else{
            poly.setCentroidIndex(checkExists.getId());
        }

        return newVertexCount;
    }

    public int addSegments(MyPolygon poly, int segmentCount){
        int newSegmentCount = segmentCount;
        //stores a list of the vertices associated with this polygon
        List <MyVertex> pVerts = poly.getVertexs();

        //Loop to create segments
        for(int i = 0; i < pVerts.size()-1; i++){
            //Create a segment
            MySegment newSegment = new MySegment(newSegmentCount, this.lineThickness);

            //set segment attributes
            newSegment.setVertex1(pVerts.get(i));
            newSegment.setVertex2(pVerts.get(i+1));
            newSegment.generateColor();
            
            //Check is segment exists
            MySegment exists = (Functions.exists(this.segments, newSegment));

            //Descide whether to add new segment to mesh
            if(exists == null){
                this.addSegment(newSegment);
                poly.addSegments(newSegment);
                newSegmentCount++;
            }else{
                poly.addSegments(exists);
            }
        }

        //create last segment
        MySegment newSegment = new MySegment(newSegmentCount, this.lineThickness);
        newSegment.setVertex1(pVerts.get(pVerts.size()-1));
        newSegment.setVertex2(pVerts.get(0));
        newSegment.generateColor();

        MySegment exists = (Functions.exists(this.segments, newSegment));

        if(exists == null){
            this.addSegment(newSegment);
            poly.addSegments(newSegment);
            newSegmentCount++;
        }else{
            poly.addSegments(exists);
        }
        
        return newSegmentCount;
    }
    public void createNeighbors(MyPolygon poly, int gridSquareHeight, int gridSquareWidth){
        if(poly.getId() - 1 > 0 && (((poly.getId())%gridSquareHeight != 0))){
            poly.addNeighbor(poly.getId() - 1);
        }

        if((poly.getId() + 1 < gridSquareHeight*gridSquareWidth) && (((poly.getId()+1)%gridSquareHeight != 0))){
            poly.addNeighbor(poly.getId() + 1);
        }

        if(poly.getId() - gridSquareHeight > 0){
            poly.addNeighbor(poly.getId() - gridSquareHeight);
        }

        if(poly.getId() + gridSquareHeight < gridSquareHeight*gridSquareWidth){
            poly.addNeighbor(poly.getId() + gridSquareHeight);
        }
    }
}//end of class gridMesh