# Smart City Graph Analytics

## Overview

This project was completed as part of the **AITU - Design and Analysis of Algorithms (DAA)** course.  
The goal of the assignment is to design and implement a set of graph algorithms to analyse different Smart City datasets and extract useful insights from complex directed graphs.

The project uses Java and Maven, with a structured workflow, unit tests, and dataset‑driven end‑to‑end validation.

---

## Objectives

The project implements a full processing pipeline for directed graphs:

1. Load graph data from JSON datasets.
2. Detect Strongly Connected Components using **Kosaraju's Algorithm**.
3. Build a **Condensation Graph**.
4. Perform **Topological Sorting** using **Kahn's Algorithm**.
5. Compute **Shortest Paths in a DAG**.
6. Compute **Longest Paths in a DAG**.
7. Measure metrics for algorithmic performance.

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
| `src/main/java/graph/Main.java` | Entry point and pipeline execution |
| `src/test/java/` | Unit and end‑to‑end tests |
| `data/` | JSON graph datasets |
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

## Datasets

Located in `/data` and used for E2E testing.

| File | Nodes | Edges | Notes |
|-------|--------|--------|--------|
| `small1_cycle.json` | Small | Few | Contains cycle (SCC expected) |
| `small2_dag.json` | Small | Few | Valid DAG |
| `small3_disconnected.json` | Small | Few | Several small components |
| `medium1_multi_scc.json` | Medium | Many | Multiple SCCs |
| `medium2_dag.json` | Medium | Many | Larger DAG |
| `medium3_weighted_complex.json` | Medium | Many | Weighted edges |
| `large1_25nodes.json` | 25 | Dense | Stress test |
| `large2_30nodes_dense.json` | 30 | Dense | Heavier stress test |
| `large3_40nodes_scc_heavy.json` | 40 | Many | Many SCCs |

---

## How to Run

### Requirements

- Java 17 or higher
- Maven
- IntelliJ IDEA (recommended)

### Run from IntelliJ

1. Open the project folder.
2. Wait for Maven to load dependencies.
3. Run `Main.java`.
4. Add Program Arguments in Run Configuration, for example:
   data/medium2_dag.json

### Run from terminal

```sh
mvn clean package
mvn exec:java -Dexec.mainClass=graph.Main -Dexec.args="data/medium2_dag.json"
```

---

## Testing

Unit tests cover each algorithm separately, plus end‑to‑end validation.

Run all tests:

```sh
mvn test
```

| Test Suite | What it validates |
|-------------|----------------------|
| `KosarajuSCCTest` | SCC detection correctness |
| `KahnTopoTest` | Valid and invalid topological sorting |
| `DagSpLpTest` | Shortest and longest path on DAG |
| `EndToEndDatasetsTest` | Full pipeline using all datasets |

Expected: **All tests must pass.**

---

## Performance & Metrics

The `Counters` class tracks operations such as:

- Node visits
- Edge relaxations
- Queue operations

This allows comparing algorithm efficiency across datasets.

Example insights:

| Dataset | SCCs Found | Longest Path Length | Notes |
|----------|---------------|------------------------|--------|
| small1_cycle | 1 | N/A | Single SCC |
| small2_dag | 0 | 3 | Clean DAG |
| medium1_multi_scc | 4+ | 6 | Mixed structure |
| large3_40nodes_scc_heavy | Many | 12+ | Most complex case |

---

## Conclusions

- Smart City graphs often contain cycles, meaning SCC detection is crucial.
- Condensation simplifies the graph, allowing easier analysis.
- DAG‑based shortest/longest path algorithms are efficient after condensation.
- The pipeline provides a scalable approach to analysing directed city graphs.

This structured approach ensures modularity, testability, and good performance on datasets of increasing size.

---

## Author

Tutkabay Assem  
AITU - DAA

