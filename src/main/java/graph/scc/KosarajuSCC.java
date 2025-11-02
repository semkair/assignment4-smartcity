package graph.scc;
import graph.Graph;
import graph.metrics.Metrics;
import java.util.*;
public class KosarajuSCC {
    public static class Result {
        public List<List<Integer>> comps=new ArrayList<>();
        public int[] compId;
    }
    public Result run(Graph g, Metrics m){
        int n=g.n; boolean[] vis=new boolean[n]; Deque<Integer> order=new ArrayDeque<>();
        for(int i=0;i<n;i++) if(!vis[i]) dfs1(g,i,vis,order,m);
        Graph gt=g.transpose(); Arrays.fill(vis,false);
        Result r=new Result(); r.compId=new int[n]; Arrays.fill(r.compId,-1);
        while(!order.isEmpty()){
            int v=order.pop(); if(vis[v]) continue;
            List<Integer> comp=new ArrayList<>(); dfs2(gt,v,vis,comp,m);
            int id=r.comps.size(); for(int u:comp) r.compId[u]=id; r.comps.add(comp);
        }
        return r;
    }
    private void dfs1(Graph g,int v,boolean[] vis,Deque<Integer> order,Metrics m){
        if(vis[v]) return; vis[v]=true; m.incSccNodeVisits();
        m.incSccEdgesScanned(g.adj.get(v).size());
        for(int[] e:g.adj.get(v)) dfs1(g,e[0],vis,order,m);
        order.push(v);
    }
    private void dfs2(Graph g,int v,boolean[] vis,List<Integer> comp,Metrics m){
        if(vis[v]) return; vis[v]=true; comp.add(v); m.incSccNodeVisits();
        m.incSccEdgesScanned(g.adj.get(v).size());
        for(int[] e:g.adj.get(v)) dfs2(g,e[0],vis,comp,m);
    }
}