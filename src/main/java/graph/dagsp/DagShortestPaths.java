package graph.dagsp;
import graph.Graph;
import graph.metrics.Metrics;
import java.util.*;
public class DagShortestPaths {
    public static class Result { public long[] dist; public int[] parent; }
    public Result run(Graph dag,int source,List<Integer> topo,Metrics m){
        int n=dag.n; long INF=Long.MAX_VALUE/4;
        long[] d=new long[n]; int[] p=new int[n];
        Arrays.fill(d,INF); Arrays.fill(p,-1); d[source]=0;
        for(int u:topo){
            if(d[u]>=INF) continue;
            for(int[] e:dag.adj.get(u)){
                int v=e[0],w=e[1]; long nd=d[u]+w;
                if(nd<d[v]){ d[v]=nd; p[v]=u; m.incSpRelax(); }
            }
        }
        DagShortestPaths.Result r=new DagShortestPaths.Result(); r.dist=d; r.parent=p; return r;
    }
    public List<Integer> reconstruct(int target,int[] parent){
        List<Integer> path=new ArrayList<>();
        for(int v=target;v!=-1;v=parent[v]) path.add(v);
        Collections.reverse(path);
        return path;
    }
}