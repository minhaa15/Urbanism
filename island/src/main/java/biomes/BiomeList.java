package biomes;

import java.util.Map;
import java.util.HashMap;

import colors.IslandColors;

import java.awt.Color;


public class BiomeList {
    private Map <String, Color> availableBiomes;

    public BiomeList(){
        availableBiomes = createAvailableBiomes();
    }

    public Color getBiome(String key){
        if(availableBiomes.containsKey(key)){
            return this.availableBiomes.get(key);
        }else{
            return this.availableBiomes.get("x");
        }
    }

    private Map<String, Color> createAvailableBiomes() {
        Map <String, Color> ab = new HashMap<>();

        //RainForest
        ab.put("rf", IslandColors.BIOME_RAIN_FOREST);

        //SeasonalForrest
        ab.put("sf", IslandColors.BIOME_SEASONAL_FORREST);

        //Dessert
        ab.put("d", IslandColors.BIOME_DESSERT);

        //Subtropical Desert
        ab.put("sd", IslandColors.BIOME_SUBTROPICAL_DESSERT);

        //Tundra
        ab.put("t", IslandColors.BIOME_TUNDRA);

        //Land
        ab.put("x", IslandColors.LAND);

        return ab;
    }
}
