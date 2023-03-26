package aquifers;

import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;

public class AquiferGenerator {
    public void createAquifiers(MyMesh mesh, int numberOfAquifers, int seed){
        List <MyPolygon> landTiles = new ArrayList<>();

        for(MyPolygon mp : mesh.getPolygons()){
            if(mp.getColor().equals(IslandColors.LAND)){
                landTiles.add(mp);
            }
        }

        List <Integer> nums = randomIsland(numberOfAquifers, landTiles.size(), seed);

        for(int n : nums){
            MyPolygon aquifer = landTiles.get(n);

            aquifer.setAquifier(true);

            // aquifer.setColor(Color.DARK_GRAY);

            landTiles.remove(aquifer);
        }
    }

    private List <Integer> randomIsland(int n, int numberOfLand, int seed){
        Random bag = new Random();

        List <Integer> nums = new ArrayList<>();

        if(seed != -1){
            bag.setSeed(seed);
        }

        for(int i = 0; i < n; i++){
            nums.add(bag.nextInt(numberOfLand));
        }

        return nums;
    }
}
