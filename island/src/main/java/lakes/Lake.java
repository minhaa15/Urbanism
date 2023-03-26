package lakes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;

public class Lake {
    public void createLakes(MyMesh mesh, int numberOfLakes, int seed){
        List <MyPolygon> landTiles = new ArrayList<>();

        for(MyPolygon mp : mesh.getPolygons()){
            if(mp.getColor().equals(IslandColors.LAND)){
                landTiles.add(mp);
            }
        }

        List <Integer> nums = randomIsland(numberOfLakes, landTiles.size(), seed);

        for(int n : nums){
            // System.out.println(seed);
            // int target = randomIsland(landTiles.size(), seed);
            // System.out.println(target);
            int size = lakeSize(seed);

            MyPolygon lake = landTiles.get(n);

            lake.setColor(IslandColors.LAKE);

            for(int j = 0; j < size; j++){
                for(int neighbour : lake.getNeighbours()){
                    if(mesh.getPolygons().get(neighbour).getColor().equals(IslandColors.LAND)){
                        mesh.getPolygons().get(neighbour).setColor(IslandColors.LAKE);
                        break;
                    }
                }
            }

            landTiles.remove(lake);
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

    private int lakeSize(int seed){
        Random bag = new Random();

        if(seed != -1){
            bag.setSeed(seed);
        }

        return bag.nextInt(3);
    }
}
