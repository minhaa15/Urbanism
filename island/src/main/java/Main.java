import java.io.IOException;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import converter.Compiler;
import converter.Extractor;
import meshcomponents.MyMesh;

public class Main {
    public static void main(String [] args) throws IOException{
        Structs.Mesh aMesh = new MeshFactory().read("img/irregular.mesh");

        Extractor extractor = new Extractor(aMesh);

        MyMesh mesh = extractor.convert();

        IslandGenerator islandGenerator = new IslandGenerator();

        islandGenerator.generate(mesh);

        Compiler compiler = new Compiler();

        Structs.Mesh exported = compiler.compile(mesh);

        new MeshFactory().write(exported, "img/lagoon.mesh");
    }
}
