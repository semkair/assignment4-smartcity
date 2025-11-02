package graph.dagsp;
import graph.Graph;
import graph.metrics.Counters;
import graph.topo.KahnTopo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
public class DagSpLpTest {
    private Graph dag(){
        Graph g=new Graph(5,true);
        g.addEdge(0,1,2);
        g.addEdge(0,2,1);
        g.addEdge(2,3,2);
        g.addEdge(1,3,4);
        g.addEdge(3,4,3);
        return g;
    }
    @Test void shortestDistances(){
        Graph g=dag();
        var topo=new KahnTopo().orderOnDAG(g,new Counters());
        var res=new DagShortestPaths().run(g,0,topo,new Counters());
        assertArrayEquals(new long[]{0,2,1,3,6},res.dist);
    }
    @Test void longestPathValue(){
        Graph g=dag();
        var topo=new KahnTopo().orderOnDAG(g,new Counters());
        var res=new DagLongestPath().run(g,0,topo,new Counters());
        assertEquals(9,res.val[4]);
    }
}