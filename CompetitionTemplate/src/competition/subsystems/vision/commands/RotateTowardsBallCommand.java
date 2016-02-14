package competition.subsystems.vision.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.vision.BallSpatialInfo;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.PropertyManager;

public class RotateTowardsBallCommand extends BaseCommand {
    static Logger log = Logger.getLogger(RotateTowardsBallCommand.class);
    
    private VisionSubsystem visionSubsystem;
    private DriveSubsystem driveSubsystem;
    
    private PIDManager rotationalPidManager;
    
    @Inject
    public RotateTowardsBallCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem, PropertyManager propMan) {
        this.visionSubsystem = visionSubsystem;
        this.driveSubsystem = driveSubsystem;
        requires(driveSubsystem);
        
        rotationalPidManager = new PIDManager("Ball rotation", propMan, 0.5, 0, 0);
    }
    
    @Override
    public void initialize() {
log.info("Initializing");
    }

    @Override
    public void execute() {
        BallSpatialInfo[] ballInfo = visionSubsystem.getBoulderInfo();
        if(ballInfo == null || ballInfo.length <= 0) {
           driveSubsystem.tankDrive(0, 0);
           log.info("Zeroing");
        }
        else {
            BallSpatialInfo targetBall = null;
            for(BallSpatialInfo ball : ballInfo) {
                if(targetBall == null || ball.distanceInches > targetBall.distanceInches) {
                    targetBall = ball;
                }
            }
            log.info("Def: " + targetBall.headingDeflection);
            double newRotationalPower = targetBall.headingDeflection / -12;
            log.info("Rot: " + newRotationalPower);
            driveSubsystem.tankDrive(newRotationalPower, -newRotationalPower);
        }
    }
    
    @Override
    public void end() {
        driveSubsystem.tankDrive(0, 0);
    }
}
