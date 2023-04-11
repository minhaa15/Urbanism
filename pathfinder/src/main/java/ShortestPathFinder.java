import java.util.*;

public class ShortestPathFinder implements PathFinder {
    private Graph graph;

    public ShortestPathFinder(Graph graph) {
        this.graph = graph;
    }

    @Override
    public List<Node> findShortestPath(Node startNode, Node endNode) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> predecessors = new HashMap<>();
        Queue<Node> queue = new PriorityQueue<>(Comparator.comparing(distances::get));
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }

        distances.put(startNode, 0);
        queue.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (visited.contains(currentNode)) {
                continue;
            }

            visited.add(currentNode);

            for (Edge edge : graph.getEdges(currentNode)) {
                Node neighborNode = edge.getDestination();
                int uncertainDistance = distances.get(currentNode) + edge.getWeight();

                if (uncertainDistance < distances.get(neighborNode)) {
                    distances.put(neighborNode, uncertainDistance);
                    predecessors.put(neighborNode, currentNode);
                    queue.add(neighborNode);
                }
            }
        }

        List<Node> shortestPath = new ArrayList<>();
        Node currentNode = endNode;

        while (currentNode != null) {
            shortestPath.add(0, currentNode);
            currentNode = predecessors.get(currentNode);
        }

        if (shortestPath.get(0) == startNode) {
            return shortestPath;
        } else {
            return null;
        }
    }
}