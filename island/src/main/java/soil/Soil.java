package soil;

import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MyVertex;

import java.util.List;


import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import colors.IslandColors;

import java.util.ArrayList;

public abstract class Soil {
    List <MyVertex> lakes;
    List <MyVertex> aquifiers; 

    public void createComposition(MyMesh mesh){
        this.lakes = new ArrayList<>();
        this.aquifiers = new ArrayList<>();

        addWater(mesh);

        function(mesh);
    }

    private void addWater(MyMesh mesh) {
        for(MyPolygon mp : mesh.getPolygons()){
            if(mp.isLake()){
                this.lakes.add(mesh.getVertexs().get(mp.getCentroidId()));
            }else if(mp.isAquifier()){
                this.aquifiers.add(mesh.getVertexs().get(mp.getCentroidId()));
            }
        }
    }

    public abstract void function(MyMesh mesh);
}
