package model;

import java.util.List;

/**
 * These are POJO (Plain Old Java Object) classes that GSON uses to
 * serialize Java objects into the output JSON file.
 */

// Represents the top-level output structure
public class OutputData {
    public List<GraphResult> results;
}

