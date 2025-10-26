import model.AlgorithmResult;
import model.Edge;
import model.GraphData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements Kruskal's algorithm to find the Minimum Spanning Tree.
 * This algorithm is greedy and builds the MST by sorting all edges
 * by weight and adding them one by one, as long as they don't
 * form a cycle.
 */
public class KruskalsAlgorithm {

    /**
     * Executes Kruskal's algorithm.
     *
     * @param graph The graph data (nodes and edges).
     * @return An AlgorithmResult object containing the MST, cost, and stats.
     */
    public static AlgorithmResult findMST(GraphData graph) {
        List<Edge> mstEdges = new ArrayList<>();
        double totalCost = 0;
        long operationCount = 0;

        // 1. Create a new list of edges to sort (avoid modifying the original)
        List<Edge> sortedEdges = new ArrayList<>(graph.edges);

        // 2. Sort all edges by weight in ascending order
        // The Edge class must implement Comparable
        Collections.sort(sortedEdges);

        // Estimate sort operations as E*log(E)
        int numEdges = sortedEdges.size();
        if (numEdges > 0) {
            // E * log2(E) approximation
            operationCount = (long) (numEdges * (Math.log(numEdges) / Math.log(2)));
        }

        // 3. Initialize a DSU structure, one set for each node
        DisjointSetUnion dsu = new DisjointSetUnion(graph.nodes);

        // 4. Iterate through all sorted edges
        for (Edge edge : sortedEdges) {
            // 5. Check if adding this edge forms a cycle.
            // This is true if 'from' and 'to' are already in the same set.
            if (dsu.union(edge.from, edge.to)) {
                // If union() returns True, they were in different sets.
                // No cycle is formed. Add this edge to the MST.
                mstEdges.add(edge);
                totalCost += edge.weight;
            }

            // Stop early if we have (V-1) edges, which is a full MST
            if (mstEdges.size() == graph.nodes.size() - 1) {
                break;
            }
        }

        // Add the DSU operations to our total operation count
        operationCount += dsu.getOperationsCount();

        // Populate the result object
        AlgorithmResult result = new AlgorithmResult();
        result.mst_edges = mstEdges;
        result.total_cost = totalCost;
        result.operations_count = operationCount;
        // Execution time will be set by the caller (MSTMain)

        return result;
    }
}
