import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import converter.Compiler;
import meshcomponents.MyMesh;

import static org.junit.jupiter.api.Assertions.*;
import Shape.*;

import meshcomponents.MyMesh;
import java.util.HashMap;
import java.util.Map;

class CircleTest{
    private Circle circle;

    @Before
    public void setUp() {
        circle = new Circle(400, mesh.getApproxCenterX(), mesh.getApproxCenterY());
    }

    @Test
    private void testInside(){
        List<MyVertex> insideVertices = new ArrayList<>();
        MyVertex in = new MyVertex();
        in.setX(mesh.getApproxCenterX());
        in.setY(mesh.getApproxCenterY());

        MyVertex out = new MyVertex();
        out.setX(mesh.getApproxCenterX() + 200);
        out.setY(mesh.getApproxCenterY() + 200);

        MyVertex inBetween = new MyVertex();
        inBetween.setX(mesh.getApproxCenterX());
        inBetween.setY(mesh.getApproxCenterY() + 200);


        assertEquals(-1, circle.inside(in));
        assertEquals(1, circle.inside(out));
        assertEquals(0, circle.inside(inBetween));
    }
}
