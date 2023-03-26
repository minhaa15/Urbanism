package biomes;

import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;

public abstract class WhittakerDiagram {

    //Note diagram is elevation by humidity
    protected String [] [] diagram;

    //Note scale
    protected int scaleY; //humidity
    protected int scaleX; //elevation

    public abstract String[][] createDiagram();

    public void generateBiomes(MyMesh mesh){
        for(MyPolygon mp : mesh.getPolygons()){
            if(mp.getColor().equals(IslandColors.LAND)){
                BiomeList bl = new BiomeList();

                String key = function(mp);
                
                mp.setColor(bl.getBiome(key));
            }
        }
    }

    public String function(MyPolygon mp){
        int row = (int)((Math.ceil((mp.getHumidity()/0.2))));

        if(row > this.diagram.length){
            row = 0;
        }else{
            row = this.diagram.length - row;
        }

        int col = (int)((Math.ceil((mp.getElevation()/100))));

        if(row > this.diagram[0].length){System.out.println(true);
            col = this.diagram[0].length - 1;
        }else{
            if(col>0){
                col = col - 1;
            }else{
                col = 0;
            }
        }//end if-else

        return this.diagram[row][col];
    }//end of function
}//end class WhittakerDiagram