package biomes;

import meshcomponents.MyPolygon;

public class KingsFort extends WhittakerDiagram{

    public KingsFort(){
        this.diagram = createDiagram();
    }
    
    public String[][] createDiagram(){
        String [] [] d = {
            {"rf","x","x","x","x","t"},
            {"rf","rf","x","x","x","t"},
            {"sf","sf","d","d","x","t"},
            {"sf","sf","d","d","t","t"},
            {"sf","sf","sd","sd","t","t"} 
        };

        return d;
    }
}//end class KingsFort