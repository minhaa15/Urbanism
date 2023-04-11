import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ShortestPathFinderTest {


    private Graph graph;
    private ShortestPathFinder shortestPathFinder;

    @Before
    public void setUp() {
        graph = new Graph();
        shortestPathFinder = new ShortestPathFinder(graph);
    }

    @Test
    public void testFindShortestPath() {
        // Create nodes
        Node a = new Node("A", "Node A", 0);
        Node b = new Node("B", "Node B", 0);
        Node c = new Node("C", "Node C", 0);
        Node d = new Node("D", "Node D", 0);
        Node e = new Node("E", "Node E", 0);
        Node f = new Node("F", "Node F", 0);

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


    @Test
    public void testFindShortestPathDisconnectedGraph() {
        // Test case with a disconnected graph
        Node a = new Node("A", "Node A", 0);
        Node b = new Node("B", "Node B", 0);
        Node c = new Node("C", "Node C", 0);
        Node d = new Node("D", "Node D", 0);
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addNode(d);
        graph.addEdge(a, b, 10, new HashMap<>());
        graph.addEdge(c, d, 5, new HashMap<>());
        List<Node> shortestPath = shortestPathFinder.findShortestPath(a, c);
        assertNull(shortestPath);
    }

    @Test
    public void testFindShortestPathNegativeWeightEdges() {
        // Test case with negative weight edges
        Node a = new Node("A", "Node A", 0);
        Node b = new Node("B", "Node B", 0);
        Node c = new Node("C", "Node C", 0);
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);
        graph.addEdge(a, b, 10, new HashMap<>());
        graph.addEdge(b, c, -5, new HashMap<>());
        List<Node> shortestPath = shortestPathFinder.findShortestPath(a, c);
        List<Node> expectedPath = Arrays.asList(a, b, c);
        assertEquals(expectedPath, shortestPath);
    }

    @Test
    public void testFindShortestPathCircularPath() {
        // Test case with a circular path
        Node a = new Node("A", "Node A", 0);
        Node b = new Node("B", "Node B", 0);
        Node c = new Node("C", "Node C", 0);
        graph.addNode(a);
        graph.addNode(b);
        graph.addNode(c);

    }
}

