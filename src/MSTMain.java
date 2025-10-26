import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Main execution class.
 * Reads input data from "ass_3_input.json",
 * runs both Prim's and Kruskal's algorithms for each graph,
 * and saves the formatted results to "ass_3_output.json".
 */
public class MSTMain {

    // Define input and output file paths
    // It looks for the input file in src/main/resources
    private static final String INPUT_FILE_PATH = "src/resources/ass_3_input.json";
    // It writes the output file to the project's root directory
    private static final String OUTPUT_FILE_PATH = "ass_3_output.json";

    public static void main(String[] args) {
        // Use Gson for JSON parsing. "setPrettyPrinting" makes the output JSON readable.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        InputData inputData = null;

        // --- 1. Read and Parse Input JSON ---
        try (Reader reader = new FileReader(INPUT_FILE_PATH)) {
            inputData = gson.fromJson(reader, InputData.class);
        } catch (Exception e) {
            System.err.println("Error reading or parsing input file '" + INPUT_FILE_PATH + "':");
            e.printStackTrace();
            return;
        }

        if (inputData == null || inputData.graphs == null) {
            System.err.println("Input file is empty or not in the correct format.");
            return;
        }

        System.out.println("Loaded " + inputData.graphs.size() + " graph(s) from '" + INPUT_FILE_PATH + "'.");

        List<GraphResult> allGraphResults = new ArrayList<>();
        OutputData outputData = new OutputData();
        outputData.results = allGraphResults;

        // --- 2. Process Each Graph ---
        for (GraphData graph : inputData.graphs) {
            System.out.println("Processing Graph ID: " + graph.id);

            // --- 3. Run Prim's Algorithm ---
            long startTimePrim = System.nanoTime();
            AlgorithmResult primResult = PrimsAlgorithm.findMST(graph);
            long endTimePrim = System.nanoTime();
            primResult.execution_time_ms = (endTimePrim - startTimePrim) / 1_000_000.0; // Convert nano to milli

            System.out.printf("  Prim's:     Cost=%.0f, Ops=%d, Time=%.2fms\n",
                    primResult.total_cost, primResult.operations_count, primResult.execution_time_ms);

            // --- 4. Run Kruskal's Algorithm ---
            long startTimeKruskal = System.nanoTime();
            AlgorithmResult kruskalResult = KruskalsAlgorithm.findMST(graph);
            long endTimeKruskal = System.nanoTime();
            kruskalResult.execution_time_ms = (endTimeKruskal - startTimeKruskal) / 1_000_000.0; // Convert nano to milli

            System.out.printf("  Kruskal's:  Cost=%.0f, Ops=%d, Time=%.2fms\n",
                    kruskalResult.total_cost, kruskalResult.operations_count, kruskalResult.execution_time_ms);

            if (primResult.total_cost != kruskalResult.total_cost) {
                System.out.println("  !!WARNING: MST costs do not match for Graph ID " + graph.id + "!!");
            }

            // --- 5. Assemble Final Result Object ---
            GraphResult graphResult = new GraphResult();
            graphResult.graph_id = graph.id;

            InputStats stats = new InputStats();
            stats.vertices = graph.nodes.size();
            stats.edges = graph.edges.size();
            graphResult.input_stats = stats;

            graphResult.prim = primResult;
            graphResult.kruskal = kruskalResult;

            allGraphResults.add(graphResult);
        }

        // --- 6. Write Results to Output JSON ---
        try (Writer writer = new FileWriter(OUTPUT_FILE_PATH)) {
            gson.toJson(outputData, writer);
            System.out.println("\nSuccessfully saved results to '" + OUTPUT_FILE_PATH + "'.");
        } catch (Exception e) {
            System.err.println("Error writing output file '" + OUTPUT_FILE_PATH + "':");
            e.printStackTrace();
        }
    }
}

