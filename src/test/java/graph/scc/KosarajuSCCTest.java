package graph.scc;
import graph.Graph;
import graph.metrics.Counters;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KosarajuSCCTest {
    @Test void tinyCycle(){
        Graph g=new Graph(3,true);
        g.addEdge(0,1,1); g.addEdge(1,2,1); g.addEdge(2,0,1);
        var r=new KosarajuSCC().run(g,new Counters());
        assertEquals(1,r.comps.size());
        assertEquals(3,r.comps.get(0).size());
    }
    @Test void threeComponents(){
        Graph g=new Graph(5,true);
        g.addEdge(0,1,1); g.addEdge(1,0,1);
        g.addEdge(3,4,1); g.addEdge(4,3,1);
        var r=new KosarajuSCC().run(g,new Counters());
        assertEquals(3,r.comps.size());
    }
}