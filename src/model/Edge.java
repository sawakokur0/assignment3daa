package model;

// Represents a single edge
// Implements Comparable to allow sorting by weight for Kruskal's
public class Edge implements Comparable<Edge> {
    public String from;
    public String to;
    public double weight; // Use double for cost

    // Default constructor (needed by some JSON libraries)
    public Edge() {}

    // Constructor for Prim's algorithm
    public Edge(String from, String to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        // Compares this edge's weight to another edge's weight
        // Used by Collections.sort() in Kruskal's
        return Double.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
    }
}
