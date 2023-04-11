
import java.util.*;

public class Graph {
    private Map<Node, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addNode(Node node) {
        this.adjacencyList.put(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node destination, int weight, Map<String, Object> attributes) {
        Edge edge = new Edge(source, destination, weight, attributes);
        this.adjacencyList.get(source).add(edge);
    }

    public List<Edge> getEdges(Node node) {
        return this.adjacencyList.get(node);
    }

    public Set<Node> getNodes() {
        return this.adjacencyList.keySet();
    }
}
