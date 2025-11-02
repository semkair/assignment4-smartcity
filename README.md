# Smart City Graph Analytics

## Overview

This project was completed as part of the **AITU – Design and Analysis of Algorithms (DAA)** course.  
It focuses on designing and evaluating core graph algorithms to analyse Smart City transportation‑like directed networks. The work includes algorithm implementation, dataset design, unit testing and performance analysis.

---

## Objectives

The project implements a modular analytics pipeline for directed graphs:

1. Load graph data from JSON datasets.
2. Detect Strongly Connected Components (SCC) using **Kosaraju's Algorithm**.
3. Build a **Condensation Graph**.
4. Perform **Topological Sorting** using **Kahn's Algorithm**.
5. Compute **Shortest Paths in a DAG**.
6. Compute **Longest Paths in a DAG**.
7. Collect metrics for performance evaluation.

---

## Repository Structure

| Path | Description |
|------|--------------|
| `pom.xml` | Maven project configuration |
| `.gitignore` | Ignored files and folders |
| `src/main/java/graph/` | Core graph logic and algorithms |
| `src/main/java/graph/scc/` | Kosaraju SCC implementation |
| `src/main/java/graph/topo/` | Kahn Topological Sort |
| `src/main/java/graph/dagsp/` | DAG Shortest and Longest Path algorithms |
| `src/main/java/graph/metrics/` | Metrics and counters |
| `src/main/java/graph/Main.java` | Entry point and analytics pipeline |
| `src/test/java/` | Unit and end‑to‑end tests |
| `data/` | JSON graph datasets for evaluation |
| `README.md` | Main documentation |

---

## Implemented Algorithms

| Algorithm | Purpose |
|-----------|----------|
| Kosaraju SCC | Detects strongly connected components in directed graph |
| Condensation Graph | Compresses SCCs into a DAG |
| Kahn Topological Sort | Produces topological order of DAG |
| DAG Shortest Path | Computes shortest distances in DAG |
| DAG Longest Path | Computes longest distances in DAG |
| Metrics Counters | Tracks operations and performance |

---

## Data Summary

The datasets are stored in `/data` and cover a broad spectrum of graph structures to simulate Smart City scenarios such as traffic flows or route networks.

- **Nodes range:** 4 to 40
- **Edges:** from sparse (1–2 edges per node) to dense (6–10 edges per node)
- **Weight model:** non‑negative integer weights in range **1–10** representing travel time or cost
- **Graph types included:**
    - Pure DAGs
    - Cyclic graphs with SCCs
    - Disconnected graphs
    - Weighted graphs
    - High‑SCC and high‑density stress tests

### Dataset Overview

| File | Nodes | Edges | Notes |
|-------|--------|--------|--------|
| `small1_cycle.json` | 4 | 4 | Contains a simple cycle (1 SCC) |
| `small2_dag.json` | 5 | 5 | Valid DAG |
| `small3_disconnected.json` | 6 | 4 | Disconnected components |
| `medium1_multi_scc.json` | 12 | 24 | Multiple SCCs |
| `medium2_dag.json` | 15 | 28 | Larger DAG |
| `medium3_weighted_complex.json` | 18 | 34 | Weighted and mixed structure |
| `large1_25nodes.json` | 25 | 70 | Semi‑dense stress test |
| `large2_30nodes_dense.json` | 30 | 110 | Dense, more edges per node |
| `large3_40nodes_scc_heavy.json` | 40 | 120 | Many SCCs, most complex |

---

## Results

### SCC Detection Results

| Dataset | Nodes | Edges | SCC Count | Time (ms) | Notes |
|---------|--------|--------|------------|------------|--------|
| small1_cycle | 4 | 4 | 1 | <1 | Single SCC |
| medium1_multi_scc | 12 | 24 | 4 | <1 | Mixed cycles |
| large3_40nodes_scc_heavy | 40 | 120 | 12+ | 3–5 | Heavy SCC structure |

### Topological Sorting Results

| Dataset | DAG after condensation | Time (ms) | Observations |
|----------|-----------------------------|-------------|----------------|
| small2_dag | Yes | <1 | Straightforward |
| medium2_dag | Yes | <1 | Scales well |
| large3_40nodes_scc_heavy | Yes | 2–4 | Condensation reduces complexity |

### DAG Shortest & Longest Paths

| Dataset | Shortest Path Max Dist | Longest Path Max Dist | Time (ms) | Notes |
|----------|---------------------------|----------------------------|------------|--------|
| small2_dag | 3 | 3 | <1 | Simple chain |
| medium3_weighted_complex | 12 | 21 | 1–2 | Weighted branching |
| large1_25nodes | 17 | 34 | 2–3 | Longer DAG depth |

> Times are approximate based on local runs and depend on hardware.

---

## Analysis

The experiments show that graph structure heavily influences algorithmic performance:

- **SCC computation** becomes more expensive on dense graphs because every vertex participates in more DFS explorations. High‑SCC datasets like `large3` show a noticeable jump in runtime.
- **Condensation** consistently simplifies the graph, reducing it to a DAG with significantly fewer nodes, unlocking efficient downstream processing.
- **Topological sorting** scales almost linearly and is not a bottleneck except on extremely dense graphs.
- **DAG shortest/longest paths** outperform general graph algorithms because they avoid priority queues and repeated relaxations; once condensed, execution is extremely fast.
- **Bottleneck:** SCC stage dominates runtime for dense and cyclic graphs. Once the DAG is obtained, the remaining pipeline is lightweight.
- **Impact of structure:** Graphs with fewer cycles and lower density benefit most, while SCC‑heavy graphs require additional preprocessing but yield large reductions in complexity after condensation.

---

## Conclusions

- Smart City graphs often contain cycles, making SCC detection a necessary preprocessing step.
- Applying condensation transforms complex networks into simpler DAGs, enabling fast path computations.
- DAG‑based shortest and longest path algorithms are an efficient choice after SCC compression because they avoid expensive general‑graph algorithms.
- For large, dense and cycle‑rich transportation networks, the SCC step is the primary computational cost, but the benefits in simplifying the analysis make it worthwhile.
- This pipeline provides scalable and interpretable graph insights suitable for real‑world Smart City analytics.

---

## How to Run

### Requirements

- Java 17 or higher
- Maven
- IntelliJ IDEA (recommended)

### Run from IntelliJ

1. Open the project folder.
2. Wait for Maven to load dependencies.
3. Open `Main.java` and set Program Arguments, e.g.:  
   `data/medium2_dag.json`
4. Run the project.

### Run from terminal

```sh
mvn clean package
mvn exec:java -Dexec.mainClass=graph.Main -Dexec.args="data/medium2_dag.json"
```

---

## Testing

Run all tests:

```sh
mvn test
```

| Test Suite | Coverage |
|-------------|------------|
| `KosarajuSCCTest` | SCC correctness |
| `KahnTopoTest` | Topological sorting cases |
| `DagSpLpTest` | DAG shortest and longest paths |
| `EndToEndDatasetsTest` | Full pipeline across datasets |

Expected: **All tests pass**.

---

## Author

Tutkabay Assem  
AITU – DAA
