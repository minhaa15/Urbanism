
import java.util.Map;

public class Edge {
    private Node source;
    private Map<String, Object> attributes;
    private int weight;
    private Node destination;



    public Edge(Node source, Node destination, int weight, Map<String, Object> attributes) {

        this.weight = weight;
        this.attributes = attributes;
        this.source = source;
        this.destination = destination;

    }

    public Node getSource() {
        return this.source;
    }

    public Node getDestination() {
        return this.destination;
    }

    public int getWeight() {
        return this.weight;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
