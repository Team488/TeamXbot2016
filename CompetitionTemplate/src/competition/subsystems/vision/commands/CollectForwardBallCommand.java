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
        BallSpatialInfo targetBall = visionSubsystem.findTargetBall();
        targetDistance = targetBall.distanceInches + 12;

        poseSubsystem.resetDistanceTraveled();
    }

    @Override
    public void execute() {
        driveSubsystem.tankDriveSafely(powerProperty.get(), powerProperty.get());
        collectorSubsystem.startIntake();
    }
    
    @Override
    public void end() {
        collectorSubsystem.stopCollector();
        driveSubsystem.stopDrive();
    }
    
    @Override
    public boolean isFinished() {
        return poseSubsystem.getRobotOrientedTotalDistanceTraveled().y >= targetDistance;
    }
}
