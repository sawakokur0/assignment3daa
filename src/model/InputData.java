package model;

import java.util.List;

/**
 * These are POJO (Plain Old Java Object) classes that GSON uses to
 * automatically parse the input JSON file into Java objects.
 * The variable names must match the keys in the JSON.
 */

// Represents the top-level structure of the input JSON
public class InputData {
    public List<GraphData> graphs;
}

