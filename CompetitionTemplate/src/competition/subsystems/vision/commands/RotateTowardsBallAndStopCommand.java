package competition.subsystems.vision.commands;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.vision.BallSpatialInfo;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RotateTowardsBallAndStopCommand extends RotateTowardsBallCommand {
    private DoubleProperty headingTolerance;
    
    public RotateTowardsBallAndStopCommand(VisionSubsystem visionSubsystem, DriveSubsystem driveSubsystem,
            XPropertyManager propMan) {
        super(visionSubsystem, driveSubsystem, propMan);
        headingTolerance = propMan.createPersistentProperty("Cam heading tolerance", 5d);
    }
    
    @Override
    public boolean isFinished() {
        BallSpatialInfo targetBall = visionSubsystem.findTargetBall();
        
        return Math.abs(targetBall.relativeHeading - cameraCenterHeading.get()) <= headingTolerance.get();
    }

}
