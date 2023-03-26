package biomes;

public class Macanada extends WhittakerDiagram{

    public Macanada(){
        this.diagram = createDiagram();
    }
    
    public String[][] createDiagram(){
        String [] [] d = {
            {"sf","sf","x","x","d","d"},
            {"x","x","x","x","d","d"},
            {"x","x","sd","sd","d","d"},
            {"x","x","sd","sd","d","d"},
            {"sd","sd","sd","sd","d","d"} 
        };

        return d;
    }

    
}//end class KingsFort