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

    private int width = 500;
    private int height = 500;
    private int square_size = 20;
    private int percision = 2;
    private float vertexThickness = 5f;
    private float lineThickness = 0.5f;
    private int lloydRelaxation = 100;
    private int numberOfPolygons = 60; 

    private int h;
    private int w;
    private String type;
    private int relaxation;
    private int nop;

    public DotGen(int h, int w, String type, int relaxation, int nop){
        this.h = h;
        this.w = w;
        this.type = type;
        this.relaxation = relaxation;
        this.nop = nop;
    }

    public Mesh generate() {
        
        // return gridMesh.compile();

        Mesh mesh = null;

        if(h != 0){
            height = h;
        }

        if(w != 0){
            width = w;
        }

        if(relaxation != 0){
            lloydRelaxation = relaxation;
        }

        if(nop != 0){
            numberOfPolygons = nop;
        }

        if((type.equals("irregular"))){
            IrregularMesh im = new IrregularMesh(percision, numberOfPolygons, height, width, vertexThickness, lineThickness, lloydRelaxation);
            im.generate();
            mesh = im.compile();
        }else if((type.equals("grid"))){
            GridMesh gridMesh = new GridMesh(width, height, square_size,percision, vertexThickness, lineThickness);
            gridMesh.generate();
            mesh = gridMesh.compile();
        }else{
            System.out.println("NO VALID MESH TYPE SELECTED");
            System.exit(0);
        }
        
        return mesh;
    }//end of method generate
}//end of DotGen