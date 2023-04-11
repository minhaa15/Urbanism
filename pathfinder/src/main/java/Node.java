import java.util.Map;

public class Node {
    private String id;
    private String name;
    private Map<String, Object> attributes;

    public Node(String id, String name, Map<String, Object> attributes) {
        this.id = id;
        this.name = name;
        this.attributes = attributes;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }
}
