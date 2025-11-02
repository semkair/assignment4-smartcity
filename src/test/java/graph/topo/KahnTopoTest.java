package graph.topo;
import graph.Graph;
import graph.metrics.Counters;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
public class KahnTopoTest {
    @Test void simpleDag(){
        Graph g=new Graph(3,true);
        g.addEdge(0,1,1); g.addEdge(0,2,1);
        var ord=new KahnTopo().orderOnDAG(g,new Counters());
        assertEquals(3,ord.size());
        assertTrue(ord.indexOf(0)<ord.indexOf(1));
        assertTrue(ord.indexOf(0)<ord.indexOf(2));
    }
    @Test void cycleThrows(){
        Graph g=new Graph(2,true);
        g.addEdge(0,1,1); g.addEdge(1,0,1);
        assertThrows(IllegalStateException.class,()->new KahnTopo().orderOnDAG(g,new Counters()));
    }
}