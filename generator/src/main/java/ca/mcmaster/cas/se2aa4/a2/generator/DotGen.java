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
            createHorizontalLines(i, verticesWithColors, segments);
            createVerticalLines(i, verticesWithColors, segments);
        }//end for loop

        //Add segments and vertices to the mesh
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }//end of method generate

    private void createHorizontalLines(int i, ArrayList<Vertex> verticesWithColors, ArrayList<Segment> segments) {
        //Create row segments, check if a right vertices exists
        if(i + 25 < verticesWithColors.size()){
            //Create property 
            Property color = createColor(i, verticesWithColors);

            //Set vertices to connect
            Segment seg = Segment.newBuilder().setV1Idx(i).setV2Idx(i+25).addProperties(color).build();

            //Add segment to list
            segments.add(seg);
        }//end if
    }//end of createHorizontalLine

    private void createVerticalLines(int i, ArrayList<Vertex> verticesWithColors, ArrayList<Segment> segments) {
        //Create column segements, checkif bottom vertices exist.
        if(((i+1)%25 != 0)){
            //Create property 
            Property color = createColor(i, verticesWithColors);

            //set verticesto connect
            Segment seg = Segment.newBuilder().setV1Idx(i).setV2Idx(i+1).addProperties(color).build();
            //add segement to list
            segments.add(seg);
        }
    }

    private Property createColor(int i, ArrayList<Vertex> verticesWithColors){
        Color c1 = extractColor(verticesWithColors.get(i).getPropertiesList());
        Color c2 = extractColor(verticesWithColors.get(i+1).getPropertiesList());

        int [][] c = {
                {c1.getRed(),c1.getGreen(), c1.getGreen()},
                {c2.getRed(),c2.getGreen(), c2.getGreen()}
        };
        int [] average = new int [3];

        for (int j = 0; j < average.length; j++){
            average[j] = (int)((c[0][j]+c[1][j])/2.0);
        }
        String colorCode = average[0] + "," + average[1] + "," + average[2];
        return Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
    }

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
    }

}
