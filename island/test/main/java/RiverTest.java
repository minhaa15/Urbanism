import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import colors.IslandColors;
import meshcomponents.MyMesh;
import meshcomponents.MyPolygon;
import meshcomponents.MySegment;
import meshcomponents.MyVertex;
import rivers.River;

public class RiverTest {

    @Test
    public void testPickPoint() {
        River river = new River();

        List<Integer> points = river.pickPoint(10, -1, 3);

        assertEquals(3, points.size());

        for (Integer p : points) {
            assertEquals(true, p >= 0 && p < 10);
        }
    }
}