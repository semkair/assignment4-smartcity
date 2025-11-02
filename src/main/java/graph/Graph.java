package graph;
import java.util.*;
public class Graph {
    public final int n;
    public final boolean directed;
    public final List<int[]> edges=new ArrayList<>();
    public final List<List<int[]>> adj;
    public Graph(int n, boolean directed){
        this.n=n; this.directed=directed;
        adj=new ArrayList<>(n);
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
    }
    public void addEdge(int u,int v,int w){
        edges.add(new int[]{u,v,w});
        adj.get(u).add(new int[]{v,w});
        if(!directed) adj.get(v).add(new int[]{u,w});
    }
    public Graph transpose(){
        Graph gt=new Graph(n,directed);
        for(int[] e:edges) gt.addEdge(e[1],e[0],e[2]);
        return gt;
    }
}