package competition.subsystems.vision.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.vision.BallSpatialInfo;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class CollectForwardBallCommand extends BaseCommand {
    static Logger log = Logger.getLogger(CollectForwardBallCommand.class);
    
    protected PoseSubsystem poseSubsystem;
    protected DriveSubsystem driveSubsystem;
    protected VisionSubsystem visionSubsystem;
    protected CollectorSubsystem collectorSubsystem;

    protected double targetDistance;
    protected boolean isInvalid = false;
    protected DoubleProperty powerProperty;

    @Inject
    public CollectForwardBallCommand(PoseSubsystem poseSubsystem, DriveSubsystem driveSubsystem,
            VisionSubsystem visionSubsystem, CollectorSubsystem collectorSubsystem, XPropertyManager propMan) {

        this.poseSubsystem = poseSubsystem;
        this.driveSubsystem = driveSubsystem;
        this.visionSubsystem = visionSubsystem;
        this.collectorSubsystem = collectorSubsystem;
        
        requires(driveSubsystem);
        requires(collectorSubsystem);

        powerProperty = propMan.createPersistentProperty("Vision collect speed", 0.4);
    }

    @Override
    public void initialize() {
        log.info("Initializing...");
        
        poseSubsystem.resetDistanceTraveled();
        
        // Travel for max distance -- driver should release the button as needed.
        targetDistance = visionSubsystem.getMaxBallAcquireDistance();
    }

    @Override
    public void execute() {
        driveSubsystem.tankDriveSafely(powerProperty.get(), powerProperty.get());
        collectorSubsystem.intake();
    }
    
    @Override
    public void end() {
        collectorSubsystem.stopCollector();
        driveSubsystem.stopDrive();
    }
}
