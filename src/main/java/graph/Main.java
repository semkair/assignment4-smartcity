package graph;
import com.fasterxml.jackson.databind.*;
import java.nio.file.*; import java.util.*;
import graph.metrics.*;
import graph.scc.KosarajuSCC; import graph.topo.KahnTopo;
import graph.dagsp.DagShortestPaths; import graph.dagsp.DagLongestPath;
public class Main {
    private static Graph contractMin(Graph g,int[] cid,int comps){
        Graph dag=new Graph(comps,true);
        Map<Integer,Map<Integer,Integer>> w=new HashMap<>();
        for(int i=0;i<comps;i++) w.put(i,new HashMap<>());
        for(int[] e:g.edges){
            int a=cid[e[0]], b=cid[e[1]];
            if(a==b) continue;
            Map<Integer,Integer> m=w.get(a);
            int val=m.getOrDefault(b,Integer.MAX_VALUE);
            if(e[2]<val) m.put(b,e[2]);
        }
        for(int a=0;a<comps;a++){
            for(var kv:w.get(a).entrySet()) dag.addEdge(a,kv.getKey(),kv.getValue());
        }
        return dag;
    }
    private static List<Integer> inducedOrder(List<Integer> compOrder,List<List<Integer>> comps){
        List<Integer> ord=new ArrayList<>();
        for(int c:compOrder) ord.addAll(comps.get(c));
        return ord;
    }
    public static void main(String[] args) throws Exception {
        String path=args.length>0?args[0]:"data/tasks.json";
        ObjectMapper om=new ObjectMapper();
        JsonNode root=om.readTree(Files.readString(Path.of(path)));
        int n=root.get("n").asInt(); boolean directed=root.get("directed").asBoolean();
        Graph g=new Graph(n,directed);
        for(JsonNode e:root.withArray("edges")) g.addEdge(e.get("u").asInt(),e.get("v").asInt(),e.get("w").asInt());
        int source=root.has("source")?root.get("source").asInt():0;
        Metrics metrics=new Counters();
        long t0=System.nanoTime();
        var scc=new KosarajuSCC().run(g,metrics);
        long t1=System.nanoTime();
        Graph dag=contractMin(g,scc.compId,scc.comps.size());
        var topo=new KahnTopo().orderOnDAG(dag,metrics);
        long t2=System.nanoTime();
        int srcComp=scc.compId[source];
        var dsp=new DagShortestPaths();
        var sp=dsp.run(dag,srcComp,topo,metrics);
        var dlp=new DagLongestPath();
        var lp=dlp.run(dag,srcComp,topo,metrics);
        long t3=System.nanoTime();
        System.out.printf("SCCs=%d time=%.3f ms%n",scc.comps.size(),(t1-t0)/1e6);
        System.out.printf("Topo DAG nodes=%d time=%.3f ms%n",dag.n,(t2-t1)/1e6);
        System.out.printf("DAG SP/LP time=%.3f ms%n",(t3-t2)/1e6);
        System.out.printf("SCC nodeVisits=%d edgesScanned=%d topoPushes=%d topoPops=%d spRelax=%d lpRelax=%d%n",metrics.sccNodeVisits(),metrics.sccEdgesScanned(),metrics.topoPushes(),metrics.topoPops(),metrics.spRelaxations(),metrics.lpRelaxations());
        System.out.println("SCCs:");
        for(int i=0;i<scc.comps.size();i++) System.out.println(i+": "+scc.comps.get(i));
        System.out.println("Topo components: "+topo);
        System.out.println("Induced order: "+inducedOrder(topo,scc.comps));
        System.out.println("SSSP distances from comp "+srcComp+": "+Arrays.toString(sp.dist));
        long best=Long.MIN_VALUE; int bestNode=-1;
        for(int i=0;i<dag.n;i++) if(lp.val[i]>best){ best=lp.val[i]; bestNode=i; }
        System.out.printf("Critical path value from comp %d to comp %d = %d%n",srcComp,bestNode,best);
        if(bestNode!=-1) System.out.println("Critical path comps: "+dlp.reconstruct(bestNode,lp.parent));
        if(dag.n>1){
            int target=bestNode;
            if(target!=-1) System.out.println("One shortest path comps to "+target+": "+dsp.reconstruct(target,sp.parent));
        }
    }
}