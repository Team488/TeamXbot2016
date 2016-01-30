package competition.subsystems.drive;

public class PoseResult {

    private boolean sane;
    private double data;
    
    public PoseResult(boolean sane, double data)
    {
        this.sane = sane;
        this.data = data;
    }
    
    public boolean isSane() {
        return sane;
    }
    
    public double getData() {
        return data;
    }
}
