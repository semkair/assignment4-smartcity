package graph.e2e;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graph.Graph;
import graph.dagsp.DagLongestPath;
import graph.dagsp.DagShortestPaths;
import graph.metrics.Counters;
import graph.topo.KahnTopo;
import graph.scc.KosarajuSCC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EndToEndDatasetsTest {

    static Stream<String> datasetFiles() {
        return Stream.of(
                "small1_cycle.json",
                "small2_dag.json",
                "small3_disconnected.json",
                "medium1_multi_scc.json",
                "medium2_dag.json",
                "medium3_weighted_complex.json",
                "large1_25nodes.json",
                "large2_30nodes_dense.json",
                "large3_40nodes_scc_heavy.json"
        );
    }

    private static Graph loadGraph(String fname) throws Exception {
        ObjectMapper om = new ObjectMapper();
        JsonNode root = om.readTree(Files.readString(Path.of("data", fname)));
        int n = root.get("n").asInt();
        boolean directed = root.get("directed").asBoolean();
        Graph g = new Graph(n, directed);
        for (JsonNode e : root.withArray("edges")) {
            g.addEdge(e.get("u").asInt(), e.get("v").asInt(), e.get("w").asInt());
        }
        return g;
    }

    private static Graph contractMin(Graph g, int[] cid, int comps) {
        Graph dag = new Graph(comps, true);
        Map<Integer, Map<Integer, Integer>> w = new HashMap<>();
        for (int i = 0; i < comps; i++) w.put(i, new HashMap<>());
        for (int[] e : g.edges) {
            int a = cid[e[0]], b = cid[e[1]];
            if (a == b) continue;
            Map<Integer, Integer> m = w.get(a);
            int val = m.getOrDefault(b, Integer.MAX_VALUE);
            if (e[2] < val) m.put(b, e[2]);
        }
        for (int a = 0; a < comps; a++) {
            for (var kv : w.get(a).entrySet()) dag.addEdge(a, kv.getKey(), kv.getValue());
        }
        return dag;
    }

    @ParameterizedTest
    @MethodSource("datasetFiles")
    @DisplayName("All datasets run end-to-end without cycles on condensation, topo size == comps, and SP/LP arrays sane")
    void allDatasetsE2E(String fname) throws Exception {
        Graph g = loadGraph(fname);
        Counters metrics = new Counters();

        var scc = new KosarajuSCC().run(g, metrics);
        int comps = scc.comps.size();
        assertTrue(comps >= 1);

        Graph dag = contractMin(g, scc.compId, comps);

        var topo = new KahnTopo().orderOnDAG(dag, metrics);
        assertEquals(comps, topo.size());

        int src = 0;
        int srcComp = scc.compId[src];

        var dsp = new DagShortestPaths().run(dag, srcComp, topo, metrics);
        var dlp = new DagLongestPath().run(dag, srcComp, topo, metrics);

        assertEquals(comps, dsp.dist.length);
        assertEquals(comps, dlp.val.length);

        assertEquals(0L, dsp.dist[srcComp]);
        assertTrue(dlp.val[srcComp] >= 0L);

        for (long x : dsp.dist) assertTrue(x >= 0 || x > Long.MIN_VALUE / 4);
    }
}
