import org.junit.Before;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.*;

import static org.junit.Assert.*;

public class ShortestPathFinderTest {


    public Graph graph;
    public ShortestPathFinder shortestPathFinder;

    @Before
    public void setUp() {
        graph = new Graph();
        shortestPathFinder = new ShortestPathFinder(graph);
    }

    @Test
    public void testFindShortestPath() {
        // Create nodes
        Node a = new Node("A", "Node A", new HashMap<>());
        Node b = new Node("B", "Node B", new HashMap<>());
        Node c = new Node("C", "Node C", new HashMap<>());
        Node d = new Node("D", "Node D", new HashMap<>());
        Node e = new Node("E", "Node E", new HashMap<>());
        Node f = new Node("F", "Node F", new HashMap<>());

        // Create graph
        Graph graph = new Graph();
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);
        graph.addNode(e);
        graph.addNode(f);

        graph.addEdge(a, b, 10, new HashMap<>());
        graph.addEdge(a, c, 15, new HashMap<>());
        graph.addEdge(b, d, 12, new HashMap<>());
        graph.addEdge(b, f, 15, new HashMap<>());
        graph.addEdge(c, e, 10, new HashMap<>());
        graph.addEdge(d, e, 2, new HashMap<>());
        graph.addEdge(d, f, 1, new HashMap<>());
        graph.addEdge(f, e, 5, new HashMap<>());

        // Create ShortestPathFinder instance
        ShortestPathFinder finder = new ShortestPathFinder(graph);

        // Test shortest path from node A to node E
        List<Node> shortestPath = finder.findShortestPath(a, e);

        assertNotNull(shortestPath);
        assertEquals(4, shortestPath.size());
        assertEquals(a, shortestPath.get(0));
        assertEquals(b, shortestPath.get(1));
        assertEquals(d, shortestPath.get(2));
        assertEquals(e, shortestPath.get(3));

        // Test shortest path from node B to node F
        shortestPath = finder.findShortestPath(b, f);
        assertNotNull(shortestPath);
        assertEquals(3, shortestPath.size());
        assertEquals(b, shortestPath.get(0));
        assertEquals(d, shortestPath.get(1));
        assertEquals(f, shortestPath.get(2));

        // Test shortest path from node C to node D
        shortestPath = finder.findShortestPath(c, d);
        assertNull(shortestPath);
    }



}

