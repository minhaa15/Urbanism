import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        Options options = new Options();

        options.addOption("h", true, "height");
        options.addOption("w", true, "width");
        options.addOption("m", true, "mesh type");
        options.addOption("p", true, "number of polygons");
        options.addOption("r", true, "relaxation");
        options.addOption("f", true, "filename");
        options.addOption("help", false, "help");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        int height = 0; 
        int width = 0; 
        String meshType = null;
        int numberOfPolygons = 0;
        int relaxation = 0; 
        String fileName = "";

        if(cmd.hasOption("h")) {
            height =  Integer.parseInt(cmd.getOptionValue("h"));
        }

        if(cmd.hasOption("w")) {
            width =  Integer.parseInt(cmd.getOptionValue("w"));
        }

        if(cmd.hasOption("m")) {

            meshType = String.valueOf(cmd.getOptionValue("m"));
            System.out.println(meshType);
        }

        if(cmd.hasOption("p")) {
            numberOfPolygons = Integer.parseInt(cmd.getOptionValue("p"));
        }

        if(cmd.hasOption("r")) {
            relaxation = Integer.parseInt(cmd.getOptionValue("r"));
        }

        if(cmd.hasOption("f")) {
            fileName = String.valueOf(cmd.getOptionValue("f"));
        }

        if(cmd.hasOption("help")) {
            System.out.println("USAGE: ");

            System.out.println("-h height of the mesh");
            System.out.println("-w width of the mesh");
            System.out.println("-m type of mesh (grid/irregular)");
            System.out.println("-r relaxation of mesh");

            System.exit(0);
        }

        DotGen generator = new DotGen(height, width, meshType, relaxation, numberOfPolygons);
        Mesh myMesh = generator.generate();
        MeshFactory factory = new MeshFactory();
        factory.write(myMesh, fileName);
    }
}
