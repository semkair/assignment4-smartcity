package graph.metrics;
public interface Metrics {
    void incSccNodeVisits();
    void incSccEdgesScanned(long k);
    void incTopoPush();
    void incTopoPop();
    void incSpRelax();
    void incLpRelax();
    long sccNodeVisits();
    long sccEdgesScanned();
    long topoPushes();
    long topoPops();
    long spRelaxations();
    long lpRelaxations();
}