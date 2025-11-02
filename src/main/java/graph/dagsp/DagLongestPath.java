package graph.dagsp;
import graph.Graph;
import graph.metrics.Metrics;
import java.util.*;
public class DagLongestPath {
    public static class Result { public long[] val; public int[] parent; }
    public Result run(Graph dag,int source,List<Integer> topo,Metrics m){
        int n=dag.n; long NEG=Long.MIN_VALUE/4;
        long[] best=new long[n]; int[] p=new int[n];
        Arrays.fill(best,NEG); Arrays.fill(p,-1); best[source]=0;
        for(int u:topo){
            if(best[u]==NEG) continue;
            for(int[] e:dag.adj.get(u)){
                int v=e[0],w=e[1]; long nv=best[u]+w;
                if(nv>best[v]){ best[v]=nv; p[v]=u; m.incLpRelax(); }
            }
        }
        DagLongestPath.Result r=new DagLongestPath.Result(); r.val=best; r.parent=p; return r;
    }
    public List<Integer> reconstruct(int target,int[] parent){
        List<Integer> path=new ArrayList<>();
        for(int v=target;v!=-1;v=parent[v]) path.add(v);
        Collections.reverse(path);
        return path;
    }
}