Assignment 3: Optimization of a City Transportation Network (MST)

This project implements and compares Prim's and Kruskal's algorithms in Java to find the Minimum Spanning Tree (MST) for a given set of city transportation networks.

The project reads graph data from src/resources/ass_3_input.json, processes it using both algorithms, and writes the complete results (MST edges, total cost, operation count, and execution time) to ass_3_output.json.

Project Structure

This project is configured with Maven, but the Java files are placed directly in the src/ directory for simplicity.

assignment3daa/
├── .idea/                 # IDE configuration folder (e.g., IntelliJ)
├── src/
│   ├── model/             # Data models for input/output JSON
│   │   ├── InputModel.java
│   │   └── OutputModel.java
│   ├── resources/           # Input data
│   │   └── ass_3_input.json
│   ├── DisjointSetUnion.java  # DSU helper class for Kruskal's
│   ├── KruskalsAlgorithm.java # Kruskal's algorithm implementation
│   ├── MSTMain.java         # Main executable class
│   └── PrimsAlgorithm.java    # Prim's algorithm implementation
├── target/                # Build output directory
├── pom.xml                # Maven configuration (for dependencies)
├── ass_3_output.json      # <-- This file is generated after running
└── report.md              # Analytical report


Prerequisites

Java Development Kit (JDK): Version 11 or newer.

Apache Maven: Required for dependency management (specifically, the Gson library).

An IDE (Recommended): An IDE like IntelliJ IDEA or Eclipse makes it much easier to manage Maven dependencies and run the project.

How to Run (Using IntelliJ IDEA)

These instructions are based on running the project directly from an IDE, which is the simplest method.

Open the Project:

In your IDE, select "Open" or "Import Project".

Navigate to and select the assignment3daa folder (the one containing pom.xml).

Sync Maven Dependencies:

The IDE should automatically detect the pom.xml file.

If you see errors on the line import com.google.gson.Gson;, your dependencies are not synced.

Find the Maven tab in your IDE (usually on the right side) and click the "Reload" or "Sync" button.

This will download the "Gson" library, and the import errors should disappear.

Run the Application:

Navigate to and open the src/MSTMain.java file.

Right-click anywhere inside the file.

Select "Run 'MSTMain.main()'".

Check the Output:

The program will execute. You will see the results printed to the "Run" console at the bottom of your IDE.

A new file, ass_3_output.json, will be created in the root of your project folder (assignment3daa/). This file contains the detailed results of the algorithm execution.