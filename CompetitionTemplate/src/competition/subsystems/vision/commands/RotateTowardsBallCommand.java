package competition.subsystems.vision.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.vision.BallSpatialInfo;
import competition.subsystems.vision.VisionStateMonitor;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.XPropertyManager;

public class RotateTowardsBallCommand extends BaseCommand {
    static Logger log = Logger.getLogger(RotateTowardsBallCommand.class);
    
    private VisionSubsystem visionSubsystem;
    private DriveSubsystem driveSubsystem;
    
    private PIDManager rotationalPidManager;
    
    @Inject
    public RotateTowardsBallCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem, XPropertyManager propMan) {
        this.visionSubsystem = visionSubsystem;
        this.driveSubsystem = driveSubsystem;
        requires(driveSubsystem);
        
        rotationalPidManager = new PIDManager("Ball rotation", propMan, 1d/20d, 0, 0);
    }
    
    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        BallSpatialInfo[] ballInfo = visionSubsystem.getBoulderInfo();
        
        if(!visionSubsystem.isConnectionHealthy() || ballInfo == null || ballInfo.length <= 0) {
           driveSubsystem.stopDrive();
        }
        else {
            BallSpatialInfo targetBall = null;
            for(BallSpatialInfo ball : ballInfo) {
                if(targetBall == null || ball.distanceInches > targetBall.distanceInches) {
                    targetBall = ball;
                }
            }

            double newRotationalPower = rotationalPidManager.calculate(0, targetBall.relativeHeading);
            driveSubsystem.tankRotateSafely(newRotationalPower);
        }
        
        visionSubsystem.updateMonitorLogging();
    }
    
    @Override
    public void end() {
        driveSubsystem.stopDrive();
    }
}
