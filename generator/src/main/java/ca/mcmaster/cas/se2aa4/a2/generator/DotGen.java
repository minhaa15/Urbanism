package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;
import java.util.ArrayList;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
//        Set<Vertex> vertices = new HashSet<>();

        ArrayList <Vertex> vertices = new ArrayList<>();

        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build());
                vertices.add(Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build());
                vertices.add(Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build());
            }
        }
        // Distribute colors randomly. Vertices are immutable, need to enrich them
//        Set<Vertex> verticesWithColors = new HashSet<>();
        Random bag = new Random();

        ArrayList <Vertex> vwc = new ArrayList<>();

        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            vwc.add(colored);
        }

        ArrayList <Vertex> verticesWithEverything = new ArrayList<>();

        for(int i = 0; i < vwc.size(); i++){
            if(i + 26 < vwc.size()){
                Color c1 = extractColor(vwc.get(i).getPropertiesList());
                Color c2 = extractColor(vwc.get(i+26).getPropertiesList());

                int [][] c = {
                        {c1.getRed(),c1.getGreen(), c1.getGreen()},
                        {c2.getRed(),c2.getGreen(), c2.getGreen()}
                };

                int [] average = new int [3];

                for (int j = 0; j < average.length; j++){
                    average[j] = (int)((c[0][j]+c[1][j])/2.0);
                }

                String colorCode = average[0] + "," + average[1] + "," + average[2];
                System.out.println("COLOR RIGHT: " + colorCode);
                Property color = Property.newBuilder().setKey("color_right").setValue(colorCode).build();
                Vertex colored = Vertex.newBuilder(vwc.get(i)).addProperties(color).build();
                verticesWithEverything.add(colored);
            }

            if(i + 1 < 26){
                Color c1 = extractColor(vwc.get(i).getPropertiesList());
                Color c2 = extractColor(vwc.get(i+1).getPropertiesList());

                int [][] c = {
                        {c1.getRed(),c1.getGreen(), c1.getGreen()},
                        {c2.getRed(),c2.getGreen(), c2.getGreen()}
                };

                int [] average = new int [3];

                for (int j = 0; j < average.length; j++){
                    average[j] = (int)((c[0][j]+c[1][j])/2.0);
                }

                String colorCode = average[0] + "," + average[1] + "," + average[2];
                System.out.println("COLOR DOWN: " + colorCode);
                Property color = Property.newBuilder().setKey("color_down").setValue(colorCode).build();
                Vertex colored = Vertex.newBuilder(vwc.get(i)).addProperties(color).build();
                verticesWithEverything.add(colored);
            }
        }

        return Mesh.newBuilder().addAllVertices(verticesWithEverything).build();
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
//                System.out.println(p.getValue());
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
