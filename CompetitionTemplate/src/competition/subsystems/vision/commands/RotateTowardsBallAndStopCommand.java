package competition.subsystems.vision.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.HeadingModule;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RotateTowardsBallAndStopCommand extends RotateTowardsBallCommand {
    private DoubleProperty headingTolerance;
    
    @Inject
    public RotateTowardsBallAndStopCommand(
            VisionSubsystem visionSubsystem,
            DriveSubsystem driveSubsystem,
            PoseSubsystem poseSubsystem,
            HeadingModule headingModule,
            XPropertyManager propMan) {
        super(visionSubsystem, driveSubsystem, poseSubsystem, propMan, headingModule);
        headingTolerance = propMan.createPersistentProperty("Cam heading tolerance", 5d);
    }
    
    @Override
    public boolean isFinished() {
        // TODO: Check rotational speed
        return !Double.isFinite(currentBallHeadingTarget.get()) || Math.abs(poseSubsystem.getCurrentHeading().difference(currentBallHeadingTarget.get()))
                <= headingTolerance.get();
    }
    
    @Override
    public void end() {
        super.end();
        log.info("Ending");
    }
}
