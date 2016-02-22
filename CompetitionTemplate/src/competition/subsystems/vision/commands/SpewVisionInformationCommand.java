package competition.subsystems.vision.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.vision.VisionSubsystem;
import edu.wpi.first.wpilibj.Timer;
import xbot.common.command.BaseCommand;

public class SpewVisionInformationCommand extends BaseCommand {
    static Logger log = Logger.getLogger(SpewVisionInformationCommand.class);
    
    private final double spewInterval = 10; // Seconds
    private double lastSpewTimestamp = Double.NEGATIVE_INFINITY;
    
    private VisionSubsystem visionSubsystem;
    
    @Inject
    public SpewVisionInformationCommand(VisionSubsystem visionSubsystem) {
        this.visionSubsystem = visionSubsystem;
        requires(visionSubsystem);
        setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {
        log.info("Initializing...");
    }

    @Override
    public void execute() {
        if(isPastInterval()) {
            updateSpewInfo();
        }
        
        visionSubsystem.updateMonitorLogging();
    }
    
    public void updateSpewInfo() {
        int numSpatial = visionSubsystem.getBoulderInfo() == null ? 0 : visionSubsystem.getBoulderInfo().length;
        log.debug("Currently has " + numSpatial + " spatial coords"
                + " (connection is " + (visionSubsystem.isConnectionHealthy() ? "healthy" : "unhealthy") + ")");
        
        this.lastSpewTimestamp = Timer.getFPGATimestamp();
    }
    
    public boolean isPastInterval() {
        return Timer.getFPGATimestamp() - lastSpewTimestamp > spewInterval;
    }
}
