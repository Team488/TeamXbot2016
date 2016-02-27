package competition.subsystems.vision.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.vision.BallSpatialInfo;
import competition.subsystems.vision.VisionStateMonitor;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RotateTowardsBallCommand extends BaseCommand {
    static Logger log = Logger.getLogger(RotateTowardsBallCommand.class);
    
    protected VisionSubsystem visionSubsystem;
    private DriveSubsystem driveSubsystem;
    
    private PIDManager rotationalPidManager;
    protected DoubleProperty cameraCenterHeading;
    
    @Inject
    public RotateTowardsBallCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem, XPropertyManager propMan) {
        this.visionSubsystem = visionSubsystem;
        this.driveSubsystem = driveSubsystem;
        requires(driveSubsystem);
        
        rotationalPidManager = new PIDManager("Ball rotation", propMan, 0.04, 0, 0);
        cameraCenterHeading = propMan.createEphemeralProperty("Centered cam heading", -15d);
    }
    
    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        BallSpatialInfo targetBall;
        if(!visionSubsystem.isConnectionHealthy() || (targetBall = visionSubsystem.findTargetBall()) == null) {
           driveSubsystem.stopDrive();
        }
        else {
            double newRotationalPower = rotationalPidManager.calculate(cameraCenterHeading.get(), targetBall.relativeHeading);
            driveSubsystem.tankRotateSafely(newRotationalPower);
        }
    }
    
    @Override
    public void end() {
        driveSubsystem.stopDrive();
    }
}
