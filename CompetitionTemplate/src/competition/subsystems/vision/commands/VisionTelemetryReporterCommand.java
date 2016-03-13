package competition.subsystems.vision.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.vision.BallSpatialInfo;
import competition.subsystems.vision.BallSpatialTemporalInfo;
import competition.subsystems.vision.VisionStateMonitor;
import competition.subsystems.vision.VisionSubsystem;
import edu.wpi.first.wpilibj.Timer;
import xbot.common.command.BaseCommand;
import xbot.common.math.XYPairManager;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class VisionTelemetryReporterCommand extends BaseCommand {
    static Logger log = Logger.getLogger(VisionTelemetryReporterCommand.class);
    
    private final double spewInterval = 10; // Seconds
    private double lastSpewTimestamp = Double.NEGATIVE_INFINITY;

    private DoubleProperty ballAngleProp;
    private DoubleProperty ballDistanceProp;
    private DoubleProperty numBallsProp;
    private DoubleProperty colorConfidenceProp;
    private DoubleProperty maxConfidenceProp;
    private BooleanProperty visionHealthProp;
    
    private VisionSubsystem visionSubsystem;
    private VisionStateMonitor monitor;
    
    @Inject
    public VisionTelemetryReporterCommand(VisionSubsystem visionSubsystem, XPropertyManager propMan) {
        this.visionSubsystem = visionSubsystem;
        setRunWhenDisabled(true);
        requires(visionSubsystem);
        
        monitor = new VisionStateMonitor(visionSubsystem);
        
        ballAngleProp = propMan.createEphemeralProperty("Target ball angle", 0d);
        ballDistanceProp = propMan.createEphemeralProperty("Target ball distance", -1d);
        colorConfidenceProp = propMan.createEphemeralProperty("Target ball color confidence", 0d);
        numBallsProp = propMan.createEphemeralProperty("Number of tracked balls", 0d);
        // TODO: Eventually we want to indicate this value to drivers, and do it more often than this
        maxConfidenceProp = propMan.createEphemeralProperty("Maximum ball confidence", 0);
        visionHealthProp = propMan.createEphemeralProperty("Is vision connection healthy?", false);
    }
    
    @Override
    public void initialize() {
        log.info("Initializing...");
    }

    @Override
    public void execute() {
        if(isPastLogInterval()) {
            updateLogInfo();
        }
        
        monitor.update();
        updateSmartDashboardProperties();
    }
    
    private void updateSmartDashboardProperties() {
        BallSpatialTemporalInfo[] ballInfo = visionSubsystem.getTrackedBoulders();
        BallSpatialInfo targetBall = visionSubsystem.findTargetBall();
        
        this.ballAngleProp.set(targetBall == null ? 0 : targetBall.relativeHeading);
        this.ballDistanceProp.set(targetBall == null ? 0 : targetBall.distanceInches);
        this.colorConfidenceProp.set(targetBall == null ? 0 : Math.round(targetBall.colorConfidence * 100));
        this.numBallsProp.set(ballInfo == null ? 0 : ballInfo.length);
        this.maxConfidenceProp.set(visionSubsystem.getMaxConfidence());
        this.visionHealthProp.set(visionSubsystem.isConnectionHealthy());
    }

    public void updateLogInfo() {
        int numSpatial = visionSubsystem.getTrackedBoulders() == null ? 0 : visionSubsystem.getTrackedBoulders().length;
        log.info("Currently has " + numSpatial + " spatial coords"
                + " (connection is " + (visionSubsystem.isConnectionHealthy() ? "healthy" : "unhealthy") + ")");
        
        this.lastSpewTimestamp = Timer.getFPGATimestamp();
    }
    
    private boolean isPastLogInterval() {
        return Timer.getFPGATimestamp() - lastSpewTimestamp > spewInterval;
    }
}
