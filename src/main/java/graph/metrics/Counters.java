package graph.metrics;
public class Counters implements Metrics {
    private long a,b,c,d,e,f;
    public void incSccNodeVisits(){a++;}
    public void incSccEdgesScanned(long k){b+=k;}
    public void incTopoPush(){c++;}
    public void incTopoPop(){d++;}
    public void incSpRelax(){e++;}
    public void incLpRelax(){f++;}
    public long sccNodeVisits(){return a;}
    public long sccEdgesScanned(){return b;}
    public long topoPushes(){return c;}
    public long topoPops(){return d;}
    public long spRelaxations(){return e;}
    public long lpRelaxations(){return f;}
}
