package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.drive.commands.UpdatePoseCommand;

import competition.subsystems.shifting.ShiftingSubsystem;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.vision.VisionSubsystem;
import competition.subsystems.vision.commands.SpewVisionInformationCommand;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems
    
    @Inject
    public void setupDriveSubsystem(
            DriveSubsystem driveSubsystem,
            TankDriveWithJoysticksCommand driveCommand,
            PoseSubsystem pose,
            UpdatePoseCommand poseCommand)
    {
        driveSubsystem.setDefaultCommand(driveCommand);
        pose.setDefaultCommand(poseCommand);
    }
    
    @Inject
    public void setupShiftingSubsystem(
            ShiftingSubsystem shiftingSubsystem,
            ShiftHighCommand shiftHighCommand) {
        shiftingSubsystem.setDefaultCommand(shiftHighCommand);
    }
    
    @Inject
    public void setupVisionSubsystem(
            VisionSubsystem visionSubsystem,
            SpewVisionInformationCommand spewer) {
        visionSubsystem.setDefaultCommand(spewer);
    }
}
