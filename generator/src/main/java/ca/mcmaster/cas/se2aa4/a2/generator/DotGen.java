package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.awt.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        //ArrayList to store positions of the vertices
        ArrayList<Vertex> vertices = new ArrayList<>();

        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
            }//end of for loop to generate vertex on columns
        }//end of for loop to generate vertex in rows

        
        // Distribute colors randomly. Vertices are immutable, need to enrich them
        ArrayList<Vertex> verticesWithColors = new ArrayList<>();

        //bag to generate random colors
        Random bag = new Random();

        //Color the created vertices
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }//end for loop to color vertices

        //ArrayList to store segments
        ArrayList <Segment> segments = new ArrayList<>();

        //code for right lines
        for(int i = 0; i < verticesWithColors.size()-1; i++){
            //Create Horizontal Lines
            if(createHorizontalLines(i, verticesWithColors) != null){
                segments.add(createHorizontalLines(i, verticesWithColors));
            }//end if
            
            //Create Vertical Lines
            if(createVerticalLines(i, verticesWithColors) != null){
                segments.add(createVerticalLines(i, verticesWithColors));
            }//end if
        }//end for loop

        //Add segments and vertices to the mesh
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }//end of method generate

    /**
     * @param i
     * @param verticesWithColors
     * @return The horizontal segment connected to that vertex
     */
    private Segment createHorizontalLines(int i, ArrayList<Vertex> verticesWithColors) {
        //Create row segments, check if a right vertices exists
        if(i + 25 < verticesWithColors.size()){
            //Create property 
            Property color = createColor(i, 25, verticesWithColors);

            //Set vertices to connect
            Segment seg = Segment.newBuilder().setV1Idx(i).setV2Idx(i+25).addProperties(color).build();

            //Add segment to list
            return seg;
        }//end if
        return null;
    }//end of createHorizontalLine

    /**
     * @param i - The Vertex to be dealt with 
     * @param verticesWithColors - The the list of vertices
     * @return The vertical segment connected to that vertex 
     * This method is responsible for drawing the Vertical Lines
     */
    private Segment createVerticalLines(int i, ArrayList<Vertex> verticesWithColors) {
        //Create column segements, checkif bottom vertices exist.
        if(((i+1)%25 != 0)){
            //Create property color of segment
            Property color = createColor(i, 1, verticesWithColors);

            //set verticesto connect
            Segment seg = Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).addProperties(color).build();
            
            //add segement to list
            return seg;
        }//end if statment
        
        //Return null if no segement is to be made
        return null;
    }// end method createVerticalLines

    /**
     * @param i 
     * @param verticesWithColors
     * @return Property representing the average color
     */
    private Property createColor(int i, int increment, ArrayList<Vertex> verticesWithColors){
        //Get color of the two vertices to be extracted
        Color c1 = extractColor(verticesWithColors.get(i).getPropertiesList());
        Color c2 = extractColor(verticesWithColors.get(i+increment).getPropertiesList());

        //Arrange RGB values of the two colors in array 
        int [][] c = {
                {c1.getRed(),c1.getGreen(), c1.getGreen()},
                {c2.getRed(),c2.getGreen(), c2.getGreen()}
        };

        //Array to store average RGB values
        int [] average = new int [3];

        //For loop to traverse through 'c' and store average value in R, G, B indicies of 'average'
        for (int j = 0; j < average.length; j++){
            average[j] = (int)((c[0][j]+c[1][j])/2.0);
        }//end for loop
        
        //Store average RGB value in string 'colorCode'
        String colorCode = average[0] + "," + average[1] + "," + average[2];

        //return Property
        return Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
    }//end method create color

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        return new Color(red, green, blue);
    }//end of method extractColor

}