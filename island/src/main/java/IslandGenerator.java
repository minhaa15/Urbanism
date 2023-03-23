import java.util.Map;

import meshcomponents.MyMesh;
import modes.LagoonIsland;

public class IslandGenerator {
    private Map<String, String> configuration;

    public IslandGenerator(Map<String, String> configuration){
        this.configuration = configuration;
    }

    public void generate(MyMesh mesh) {
        if(configuration.get("mode").equals("lagoon")){
            LagoonIsland lagoonIsland = new LagoonIsland();
            lagoonIsland.generate(mesh);
        }
    }
}
