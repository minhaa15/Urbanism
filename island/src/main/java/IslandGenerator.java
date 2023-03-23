import meshcomponents.MyMesh;
import modes.LagoonIsland;

public class IslandGenerator {

    public void generate(MyMesh mesh) {
        LagoonIsland lagoonIsland = new LagoonIsland();
        lagoonIsland.generate(mesh);
    }
}
