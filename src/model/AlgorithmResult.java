package model;

import java.util.List;

// Represents the results from a single algorithm (Prim's or Kruskal's)
public class AlgorithmResult {
    public List<Edge> mst_edges;
    public double total_cost;
    public long operations_count;
    public double execution_time_ms;
}
