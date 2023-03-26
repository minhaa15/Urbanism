import org.junit.jupiter.api.Test;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import converter.Compiler;
import meshcomponents.MyMesh;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;


public class IslandCreationTest {

    @Test
    public void meshIsLagoon() {
        Map <String, String> config = new HashMap<>();

        config.put("i", "img/irregular.mesh");
        config.put("o", "img/lagoon.mesh");
        config.put("shape", "circle");
        config.put("mode", "lagoon");
        config.put("shape", "circle");
        config.put("lakes", "10");
        config.put("aquifiers", "4");
        config.put("soil", "dessertsoil");
        config.put("biomes", "macanada");
        config.put("altitude", "mountain");
        config.put("rivers", "10");
        config.put("seed", "1");

        //java -jar island/island.jar -i img/irregular.mesh -o img/lagoon.mesh --mode custom --shape circle --lakes 10 --aquifiers 4 --soil dessertsoil --biomes macanada --altitude mountain --rivers 10 --seed 1


        Structs.Mesh aMesh = new MeshFactory().read(config.get("i"));

        Extractor extractor = new Extractor(aMesh);

        MyMesh mesh = extractor.convert();

        IslandGenerator islandGenerator = new IslandGenerator(configuration.export());

        islandGenerator.generate(mesh);

        converter.Compiler compiler = new Compiler();

        Structs.Mesh exported = compiler.compile(mesh);

        assertNotNull(exported);
    }

    @Test
    public void meshIsNotLagoon() {
        Map <String, String> config = new HashMap<>();

        config.put("i", "img/irregular.mesh");
        config.put("o", "img/lagoon.mesh");
        config.put("shape", "circle");
        config.put("mode", "custom");
        config.put("shape", "circle");
        config.put("lakes", "10");
        config.put("aquifiers", "4");
        config.put("soil", "dessertsoil");
        config.put("biomes", "macanada");
        config.put("altitude", "mountain");
        config.put("rivers", "10");
        config.put("seed", "1");

        //java -jar island/island.jar -i img/irregular.mesh -o img/lagoon.mesh --mode custom --shape circle --lakes 10 --aquifiers 4 --soil dessertsoil --biomes macanada --altitude mountain --rivers 10 --seed 1


        Structs.Mesh aMesh = new MeshFactory().read(config.get("i"));

        Extractor extractor = new Extractor(aMesh);

        MyMesh mesh = extractor.convert();

        IslandGenerator islandGenerator = new IslandGenerator(configuration.export());

        islandGenerator.generate(mesh);

        converter.Compiler compiler = new Compiler();

        Structs.Mesh exported = compiler.compile(mesh);

        assertNotNull(exported);
    }
}
