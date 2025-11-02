package graph.topo;
import graph.Graph;
import graph.metrics.Metrics;
import java.util.*;
public class KahnTopo {
    public List<Integer> orderOnDAG(Graph dag, Metrics m){
        int n=dag.n; int[] indeg=new int[n];
        for(int u=0;u<n;u++) for(int[] e:dag.adj.get(u)) indeg[e[0]]++;
        ArrayDeque<Integer> q=new ArrayDeque<>();
        for(int i=0;i<n;i++) if(indeg[i]==0){ q.add(i); m.incTopoPush(); }
        List<Integer> ord=new ArrayList<>();
        while(!q.isEmpty()){
            int u=q.remove(); ord.add(u); m.incTopoPop();
            for(int[] e:dag.adj.get(u)) if(--indeg[e[0]]==0){ q.add(e[0]); m.incTopoPush(); }
        }
        if(ord.size()!=n) throw new IllegalStateException("Not a DAG");
        return ord;
    }
}
