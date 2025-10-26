import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * A Disjoint Set Union (DSU) or Union-Find data structure.
 * Used by Kruskal's algorithm to efficiently detect cycles.
 * It uses path compression and union by rank for optimization.
 */
public class DisjointSetUnion {
    private Map<String, String> parent;
    private Map<String, Integer> rank;
    private long operations; // Counter for find/union operations

    /**
     * Initializes the DSU structure.
     * Each node starts as its own parent (in its own set).
     *
     * @param nodes A list of node identifiers.
     */
    public DisjointSetUnion(List<String> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        operations = 0;
        for (String node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
        }
    }

    /**
     * Finds the representative (root) of the set containing 'node'.
     * Uses path compression to flatten the tree.
     *
     * @param node The node to find the set for.
     * @return The representative (root) of the set.
     */
    public String find(String node) {
        operations++;
        String root = parent.get(node);
        if (root.equals(node)) {
            return node;
        }

        // Path compression:
        // Recursively find the root and set it as the parent
        // of all nodes along the path.
        root = find(parent.get(node));
        parent.put(node, root);
        return root;
    }

    /**
     * Merges the sets containing 'node1' and 'node2'.
     * Uses union by rank to keep the tree balanced.
     *
     * @param node1 The first node.
     * @param node2 The second node.
     * @return true if a union was performed (nodes were in different sets),
     * false otherwise (nodes were already in the same set).
     */
    public boolean union(String node1, String node2) {
        operations++;
        String root1 = find(node1);
        String root2 = find(node2);

        if (!root1.equals(root2)) {
            // Union by rank:
            // Attach the smaller-rank tree to the root of the higher-rank tree.
            int rank1 = rank.get(root1);
            int rank2 = rank.get(root2);

            if (rank1 > rank2) {
                parent.put(root2, root1);
            } else if (rank1 < rank2) {
                parent.put(root1, root2);
            } else {
                // If ranks are equal, pick one as parent and increment its rank.
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the total count of find/union operations.
     *
     * @return The operations count.
     */
    public long getOperationsCount() {
        return operations;
    }
}
