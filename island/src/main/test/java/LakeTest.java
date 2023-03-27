import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;

public class LakeTest {
    private Lake lake;

    @Before
    public void setUp() {
        lake = new Lake();
    }

    @Test
    public void testCreateLakes() {
        // create a small mesh with 5 land polygons
        MyMesh mesh = new MyMesh();
        mesh.getPolygons().add(new MyPolygon(new ArrayList<>(), IslandColors.LAND));
        mesh.getPolygons().add(new MyPolygon(new ArrayList<>(), IslandColors.LAND));
        mesh.getPolygons().add(new MyPolygon(new ArrayList<>(), IslandColors.LAND));
        mesh.getPolygons().add(new MyPolygon(new ArrayList<>(), IslandColors.LAND));
        mesh.getPolygons().add(new MyPolygon(new ArrayList<>(), IslandColors.LAND));

        // create 2 lakes
        int numberOfLakes = 2;
        int seed = 12345;
        lake.createLakes(mesh, numberOfLakes, seed);

        // check that the correct number of polygons are now lakes
        int numberOfLakesCreated = 0;
        for (MyPolygon mp : mesh.getPolygons()) {
            if (mp.getColor().equals(IslandColors.LAKE)) {
                numberOfLakesCreated++;
            }
        }
        assertEquals(numberOfLakes, numberOfLakesCreated);

        // check that the lakes are connected to their original polygon
        for (MyPolygon mp : mesh.getPolygons()) {
            if (mp.getColor().equals(IslandColors.LAKE)) {
                boolean connectedToLand = false;
                for (int neighbour : mp.getNeighbours()) {
                    if (mesh.getPolygons().get(neighbour).getColor().equals(IslandColors.LAND)) {
                        connectedToLand = true;
                    }
                }
                assertTrue(connectedToLand);
            }
        }
    }

    @Test
    public void testRandomIsland() {
        int n = 5;
        int numberOfLand = 10;
        int seed = 1;

        List<Integer> nums = lake.randomIsland(n, numberOfLand, seed);

        // check that the correct number of integers are generated
        assertEquals(n, nums.size());

        // check that each integer is less than numberOfLand
        for (int i : nums) {
            assertTrue(i < numberOfLand);
        }
    }

    @Test
    public void testLakeSize() {
        int seed = 5;
        int size = lake.lakeSize(seed);

        // check that size is between 0 and 2 inclusive
        assertTrue(size >= 0);
        assertTrue(size <= 2);
    }
}