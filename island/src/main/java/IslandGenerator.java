import java.awt.Color;
import java.util.Map;
import java.util.Random;

import aquifers.AquiferGenerator;
import biomes.KingsFort;
import biomes.Macanada;
import colors.IslandColors;
import elevation.Mountain;
import elevation.Valley;
import lakes.Lake;
import meshcomponents.MyMesh;
import meshcomponents.MySegment;
import modes.Lagoon;
import rivers.River;
import shapes.Rectangle;
import shapes.Triangle;
import soil.BasicSoil;
import soil.DessertSoil;
import shapes.Circle;

public class IslandGenerator {
    private Map<String, String> configuration;

    public IslandGenerator(Map<String, String> configuration){
        this.configuration = configuration;
    }

    public void generate(MyMesh mesh) {
        //create island
        if(configuration.get("shape").equals("circle")){
            Circle circle = new Circle(600, mesh.getApproxCenterX(), mesh.getApproxCenterY());
        
            circle.setBorderColor(IslandColors.BEACH);
            circle.setInsideColor(IslandColors.LAND);
            circle.setOutsideColor(IslandColors.OCEAN);

            circle.draw(mesh);
        }else if (configuration.get("shape").equals("rectangle")){
            Rectangle rect = new Rectangle(1040, 1040, mesh.getApproxCenterX(), mesh.getApproxCenterY());
        
            rect.setBorderColor(IslandColors.BEACH);
            rect.setInsideColor(IslandColors.LAND);
            rect.setOutsideColor(IslandColors.OCEAN);

            rect.draw(mesh);
        }else if(configuration.get("shape").equals("triangle")){
            Triangle tri = new Triangle(1040, mesh.getApproxCenterX(), mesh.getApproxCenterY());

            tri.setBorderColor(IslandColors.BEACH);
            tri.setInsideColor(IslandColors.LAND);
            tri.setOutsideColor(IslandColors.OCEAN);

            tri.draw(mesh);
        }else{
            Circle circle = new Circle(600, mesh.getApproxCenterX(), mesh.getApproxCenterY());
        
            circle.setBorderColor(IslandColors.BEACH);
            circle.setInsideColor(IslandColors.LAND);
            circle.setOutsideColor(IslandColors.OCEAN);

            circle.draw(mesh);
        }

        if(configuration.get("mode").equals("lagoon")){
            Lagoon lagoonIsland = new Lagoon();
            lagoonIsland.generate(mesh, configuration.get("shape"));
        }else{
            int seed = -1;
            if(configuration.get("seed") != null){
                seed = Integer.parseInt(configuration.get("seed"));
            }

            //custom island
            if(configuration.get("altitude") != null){
                if(configuration.get("altitude").equals("mountain")){
                    Mountain m = new Mountain(mesh.getApproxCenterX(), mesh.getApproxCenterY(), 5, 600);
                    m.createElevation(mesh);
                }
            }else if(configuration.get("altitude").equals("valley")){
                Valley m = new Valley(mesh.getApproxCenterX(), mesh.getApproxCenterY(), 5, 600);
                m.createElevation(mesh);
            }

            if(!configuration.get("rivers").isEmpty()){
                River river = new River();

                river.createRiver(mesh, Integer.parseInt(configuration.get("rivers")), seed);
            }

            System.out.println("TEST");

            if(!configuration.get("lakes").isEmpty()){
                Lake lake = new Lake();

                lake.createLakes(mesh, Integer.parseInt(configuration.get("lakes")), seed);
            }

            if(!configuration.get("aquifiers").isEmpty()){
                AquiferGenerator ag = new AquiferGenerator();

                ag.createAquifiers(mesh, Integer.parseInt(configuration.get("aquifiers")), seed);
            }

            if(configuration.get("soil").equals("basicsoil")){
                BasicSoil bs = new BasicSoil();

                bs.createComposition(mesh);
            }else if(configuration.get("soil").equals("dessetsoil")){
                DessertSoil ds = new DessertSoil();

                ds.createComposition(mesh);
            }else{
                BasicSoil bs = new BasicSoil();

                bs.createComposition(mesh);
            }

            if(configuration.get("biomes").equals("kingsfort")){
                KingsFort kf = new KingsFort();

                kf.generateBiomes(mesh);
            }else if(configuration.get("biomes").equals("macanada")) {
                Macanada kf = new Macanada();

                kf.generateBiomes(mesh);
            }else{
                KingsFort kf = new KingsFort();

                kf.generateBiomes(mesh);
            }
        }
    }
}
