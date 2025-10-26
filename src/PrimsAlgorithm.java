import model.AlgorithmResult;
import model.Edge;
import model.GraphData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Implements Prim's algorithm to find the Minimum Spanning Tree.
 * This algorithm is greedy and builds the MST by starting with an
 * arbitrary node and iteratively adding the cheapest possible
 * edge that connects a node in the MST to a node outside the MST.
 */
public class PrimsAlgorithm {

    /**
     * Helper class for the PriorityQueue in Prim's.
     * Stores a destination node and its connecting edge weight.
     * Implements Comparable to create a min-heap based on weight.
     */
    private static class HeapNode implements Comparable<HeapNode> {
        String node;
        double weight;
        String fromNode; // The node *in* the MST this edge comes from

        HeapNode(String from, String to, double weight) {
            this.fromNode = from;
            this.node = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(HeapNode other) {
            return Double.compare(this.weight, other.weight);
        }
    }

    /**
     * Executes Prim's algorithm.
     *
     * @param graph The graph data (nodes and edges).
     * @return An AlgorithmResult object containing the MST, cost, and stats.
     */
    public static AlgorithmResult findMST(GraphData graph) {
        if (graph.nodes == null || graph.nodes.isEmpty()) {
            return new AlgorithmResult(); // Return empty result
        }

        long operationCount = 0;
        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0;

        // Keep track of nodes already included in the MST.
        Set<String> visited = new HashSet<>();

        // Use a min-heap (PriorityQueue) to store edges to visit.
        PriorityQueue<HeapNode> minHeap = new PriorityQueue<>();

        // Build an adjacency list for efficient neighbor lookup
        Map<String, List<Edge>> adjList = buildAdjList(graph.nodes, graph.edges);

        // 1. Start from the first node in the list
        String startNode = graph.nodes.get(0);
        visited.add(startNode);
        operationCount++; // Count adding to visited

        // 2. Add all edges from the start node to the heap
        for (Edge edge : adjList.getOrDefault(startNode, new ArrayList<>())) {
            String neighbor = edge.from.equals(startNode) ? edge.to : edge.from;
            minHeap.add(new HeapNode(startNode, neighbor, edge.weight));
            operationCount++; // Count heap add
        }

        while (!minHeap.isEmpty() && visited.size() < graph.nodes.size()) {
            // 3. Get the cheapest edge connecting the MST to a new node
            HeapNode cheapestNode = minHeap.poll();
            operationCount++; // Count heap poll

            operationCount++; // Count 'contains' check
            if (visited.contains(cheapestNode.node)) {
                // If the destination node is already in the MST, this edge
                // would form a cycle. Skip it.
                continue;
            }

            // 4. Add the new node to the MST
            visited.add(cheapestNode.node);
            operationCount++; // Count adding to visited

            // 5. Add the edge to our MST list
            mstEdges.add(new Edge(cheapestNode.fromNode, cheapestNode.node, cheapestNode.weight));
            totalCost += cheapestNode.weight;

            // 6. Add all edges from the newly added node to the heap,
            //    if they lead to unvisited nodes.
            for (Edge edge : adjList.getOrDefault(cheapestNode.node, new ArrayList<>())) {
                String neighbor = edge.from.equals(cheapestNode.node) ? edge.to : edge.from;

                operationCount++; // Count 'contains' check
                if (!visited.contains(neighbor)) {
                    minHeap.add(new HeapNode(cheapestNode.node, neighbor, edge.weight));
                    operationCount++; // Count heap add
                }
            }
        }

        // Populate the result object
        AlgorithmResult result = new AlgorithmResult();
        result.mst_edges = mstEdges;
        result.total_cost = totalCost;
        result.operations_count = operationCount;
        // Execution time will be set by the caller (MSTMain)

        return result;
    }

    /**
     * Helper function to build an adjacency list from node and edge lists.
     * The graph is undirected, so add edges in both directions.
     */
    private static Map<String, List<Edge>> buildAdjList(List<String> nodes, List<Edge> edges) {
        Map<String, List<Edge>> adjList = new HashMap<>();
        for (String node : nodes) {
            adjList.put(node, new ArrayList<>());
        }
        for (Edge edge : edges) {
            adjList.get(edge.from).add(edge);
            adjList.get(edge.to).add(edge);
        }
        return adjList;
    }
}
