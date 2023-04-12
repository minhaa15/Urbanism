import java.io.IOException;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import citycreator.FactoryCity;
import converter.Compiler;
import converter.Extractor;
import meshcomponents.MyMesh;

public class Main {
    public static void main(String [] args) throws IOException{
        Configuration configuration = new Configuration(args);

        Structs.Mesh aMesh = new MeshFactory().read(configuration.input());

        Extractor extractor = new Extractor(aMesh);

        MyMesh mesh = extractor.convert();

        IslandGenerator islandGenerator = new IslandGenerator(configuration.export());

        islandGenerator.generate(mesh);

        //Create 20 cities for the test
        int numberOfCities = 20;

        if(configuration.export().get("cities") != null){
            numberOfCities = Integer.parseInt(configuration.export().get("cities"));
        }

        FactoryCity cf = new FactoryCity();
        List <Integer> cities = cf.createCities(numberOfCities, mesh);

        Compiler compiler = new Compiler();

        Structs.Mesh exported = compiler.compile(mesh);

        new MeshFactory().write(exported, configuration.output());
    }
}
