package citycreator;

import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MyVertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FactoryCity {

    public List<Integer> createCities(int numberOfCities, MyMesh mesh) {
        List<MyPolygon> landTiles = getLandTiles(mesh);
        List<Integer> centroidsUsed = new ArrayList<>();

        for (int i = 0; i < numberOfCities && !landTiles.isEmpty(); i++) {
            int tile = random(0, landTiles.size() - 1);
            MyPolygon polygon = landTiles.remove(tile);
            int centroidId = polygon.getCentroidId();
            centroidsUsed.add(polygon.getId());

            MyVertex mv = mesh.getVertexs().get(centroidId);
            int citySize = random(1, 4);
            mv.setCitySize(citySize);
        }

        return centroidsUsed;
    }

    private List<MyPolygon> getLandTiles(MyMesh mesh) {
        List<MyPolygon> landTiles = new ArrayList<>();

        for (MyPolygon polygon : mesh.getPolygons()) {
            if (!polygon.getColor().equals(IslandColors.LAKE) && !polygon.getColor().equals(IslandColors.OCEAN)) {
                landTiles.add(polygon);
            }
        }

        return landTiles;
    }

    private int random(int start, int end) {
        if (start >= end) {
            return start;
        }

        Random bag = new Random();
        return bag.nextInt(end - start + 1) + start;
    }
}
