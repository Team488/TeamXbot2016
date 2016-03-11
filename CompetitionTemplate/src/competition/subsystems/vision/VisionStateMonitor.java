package competition.subsystems.vision;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import xbot.common.logic.Latch;
import xbot.common.logic.Latch.EdgeType;

public class VisionStateMonitor implements Observer {
    
    static Logger log = Logger.getLogger(VisionStateMonitor.class);
    
    private VisionSubsystem subsystem;
    private Latch healthLatch;
    
    public VisionStateMonitor(VisionSubsystem subsystem) {
        this.subsystem = subsystem;
        
        healthLatch = new Latch(false, EdgeType.Both);
        healthLatch.addObserver(this);
    }
    
    public void update() {
        healthLatch.setValue(subsystem.isConnectionHealthy());
    }

    @Override
    public void update(Observable o, Object arg) {
        switch((EdgeType)arg) {
            case FallingEdge:
                log.warn("Vision connection is unhealthy!");
                break;
            case RisingEdge:
                log.info("Vision connection is healthy.");
                break;
            default:
                break;
        }
    }
}
