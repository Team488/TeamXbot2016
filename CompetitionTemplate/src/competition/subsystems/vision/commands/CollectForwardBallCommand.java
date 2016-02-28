package competition.subsystems.vision.commands;

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

    protected PoseSubsystem poseSubsystem;
    protected DriveSubsystem driveSubsystem;
    protected VisionSubsystem visionSubsystem;
    protected CollectorSubsystem collectorSubsystem;

    protected double targetDistance;
    protected boolean isInvalid = false;
    protected DoubleProperty powerProperty;
    protected DoubleProperty maxDistanceProperty;

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
        maxDistanceProperty = propMan.createPersistentProperty("Max ball collect dist", 96d);
    }

    @Override
    public void initialize() {
        poseSubsystem.resetDistanceTraveled();
        
        BallSpatialInfo targetBall = visionSubsystem.findTargetBall();
        
        if(targetBall == null || targetBall.distanceInches > maxDistanceProperty.get()) {
            targetDistance = 0;
            isInvalid = true;
            
            return;
        }
        
        targetDistance = targetBall.distanceInches + 12;
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
    
    @Override
    public boolean isFinished() {
        return isInvalid || poseSubsystem.getRobotOrientedTotalDistanceTraveled().y >= targetDistance;
    }
}
