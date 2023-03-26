package soil;

import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MyVertex;

public class DessertSoil extends Soil{
    
    public void function(MyMesh mesh){
        for(MyPolygon poly : mesh.getPolygons()){
            if(poly.getColor().equals(IslandColors.LAND) && !(poly.isAquifier())){
                double weight = 0.1;

                for(MyVertex v : this.lakes){
                    weight += 1/((distance(v, mesh.getVertexs().get(poly.getCentroidId())))/5);
                }//end for loop
                
                
                for(MyVertex v : this.aquifiers){
                    weight += 0.6/((distance(v, mesh.getVertexs().get(poly.getCentroidId())))/5);
                }//end for loop

                if(poly.getElevation() > 0){
                    weight -= 0.1;
                }else{
                    weight += 0.1;
                }//end if else tree

                poly.setHumidity(weight);
            }
        }
    }//end method function

    public double distance(MyVertex v1, MyVertex v2){
        return Math.sqrt((Math.pow((v1.getX() - v2.getX()), 2))+(Math.pow(v1.getY() - v2.getY(), 2)));
    }//end method distance
}//end of class