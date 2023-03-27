import static org.junit.Assert.*;
import org.junit.*;

import meshcomponents.*;
import colors.*;

public class MountainTest {
    private MyMesh mesh;
    private Mountain mountain;

    @Test
    public void testInside() {
        // Test a vertex outside the mountain
        MyVertex in = new MyVertex();
        in.setX(mesh.getApproxCenterX());
        in.setY(mesh.getApproxCenterY());

        assertFalse(mountain.inside(in);

        // Test a vertex inside the mountain
        MyVertex v = new MyVertex();
        in.setX(5);
        in.setY(5);
        assertTrue(mountain.inside(new MyVertex[] {v}));
    }

    @Test
    public void testFindRadius() {
        // Test finding the radius of a vertex at the center of the mountain
        assertEquals(0, mountain.findRadius(5, 5, 5, 5), 0.001);

        // Test finding the radius of a vertex outside the mountain
        assertEquals(5, mountain.findRadius(10, 10, 5, 5), 0.001);

        // Test finding the radius of a vertex inside the mountain
        assertEquals(2.5 * Math.sqrt(2), mountain.findRadius(7.5, 7.5, 5, 5), 0.001);
    }
}