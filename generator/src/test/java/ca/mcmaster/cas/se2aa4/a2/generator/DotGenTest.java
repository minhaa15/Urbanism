package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DotGenTest {

    @Test
    public void meshIsNotNull() {
        DotGen generator = new DotGen(0, 0, "irregular", 0,0);
        Structs.Mesh aMesh = generator.generate();
        assertNotNull(aMesh);
    }

    @Test
    public void meshValidHeightWidth() {
        DotGen generator = new DotGen(500, 500, "irregular", 0,0);
        Structs.Mesh aMesh = generator.generate();
        assertNotNull(aMesh);
    }

    @Test
    public void meshNullType() {
        DotGen generator = new DotGen(0, 0, null, 0,0);
        Structs.Mesh aMesh = generator.generate();
        assertNotNull(aMesh);
    }

    @Test
    public void meshRelaxationTest() {
        DotGen generator = new DotGen(500, 500, "irregular", 10,40);
        Structs.Mesh aMesh = generator.generate();
        assertNotNull(aMesh);
    }

    @Test
    public void gridMeshTest() {
        DotGen generator = new DotGen(500, 500, "grid", 10,40);
        Structs.Mesh aMesh = generator.generate();
        assertNotNull(aMesh);
    }
}
