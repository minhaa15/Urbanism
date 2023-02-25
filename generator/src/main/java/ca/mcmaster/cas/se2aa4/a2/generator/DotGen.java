package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;
import java.awt.*;
import customMesh.*;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;
    private final int percision = 2;
    private final float vertexThickness = 3f;
    private final float lineThickness = 0.5f;

    public Mesh generate() {
        GridMesh gridMesh = new GridMesh(width, height, square_size,percision, vertexThickness, lineThickness);
        gridMesh.generate();

        return gridMesh.compile();
    }//end of method generate
}//end of DotGen