package competition.subsystems.vision.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.HeadingModule;
import competition.subsystems.vision.BallSpatialInfo;
import competition.subsystems.vision.VisionStateMonitor;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RotateTowardsBallCommand extends BaseCommand {
    static Logger log = Logger.getLogger(RotateTowardsBallCommand.class);
    
    protected VisionSubsystem visionSubsystem;
    protected DriveSubsystem driveSubsystem;
    protected PoseSubsystem poseSubsystem;
    protected HeadingModule headingModule;
    
    protected PIDManager rotationalPidManager;
    protected DoubleProperty cameraCenterHeading;
    protected DoubleProperty currentBallHeadingTarget;
    
    @Inject
    public RotateTowardsBallCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem, PoseSubsystem poseSubsystem, XPropertyManager propMan, HeadingModule headingModule) {
        this.visionSubsystem = visionSubsystem;
        this.driveSubsystem = driveSubsystem;
        this.headingModule = headingModule;
        requires(driveSubsystem);
        
        this.headingModule.setPIDPropertyName("Ball rotate");
        
        cameraCenterHeading = propMan.createEphemeralProperty("Centered cam heading", -15d);
        currentBallHeadingTarget = propMan.createEphemeralProperty("Current ball target", Double.POSITIVE_INFINITY);
    }
    
    @Override
    public void initialize() {
        log.info("Initializing");
        
        BallSpatialInfo targetBall = visionSubsystem.findTargetBall();
        if(targetBall == null) {
            log.warn("No target ball found! Nothing to track.");
            currentBallHeadingTarget.set(Double.POSITIVE_INFINITY);
        }
        
        currentBallHeadingTarget.set(
                poseSubsystem.getCurrentHeading()
                    .shiftValue(targetBall.relativeHeading)
                    .getValue());
    }

    @Override
    public void execute() {
        if(Double.isFinite(currentBallHeadingTarget.get())) {
            driveSubsystem.tankRotateSafely(headingModule.calculateHeadingPower(currentBallHeadingTarget.get()));
        }
    }
    
    @Override
    public void end() {
        driveSubsystem.stopDrive();
    }
}
