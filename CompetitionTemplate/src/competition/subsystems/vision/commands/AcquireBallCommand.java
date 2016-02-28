package competition.subsystems.vision.commands;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.HeadingModule;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommandGroup;
import xbot.common.properties.XPropertyManager;

public class AcquireBallCommand extends BaseCommandGroup {
    @Inject
    public AcquireBallCommand(DriveSubsystem driveSubsystem, HeadingModule headingModule, PoseSubsystem pose,
            CollectorSubsystem collector, VisionSubsystem visionSubsystem, XPropertyManager propMan) {
        
        RotateTowardsBallAndStopCommand rotateCommand = new RotateTowardsBallAndStopCommand(visionSubsystem, driveSubsystem, propMan);
        this.addSequential(rotateCommand);
        
        CollectForwardBallCommand collectCommand = new CollectForwardBallCommand(pose, driveSubsystem, visionSubsystem, collector, propMan);
        this.addSequential(collectCommand);
    }
}
