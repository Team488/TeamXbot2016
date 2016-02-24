package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.arm_commands.ArmAngleMaintainerCommand;
import competition.subsystems.arm.arm_commands.ArmManualControlCommand;
import competition.subsystems.arm.arm_commands.StopCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.drive.commands.UpdatePoseCommand;
import competition.subsystems.hanger.HookSubsystem;
import competition.subsystems.hanger.WinchSubsystem;
import competition.subsystems.hanger.hook_commands.copy.HookStopCommand;
import competition.subsystems.hanger.winch_commands.WinchStopCommand;
import competition.subsystems.portcullis_wheels.PortcullisWheelsSubsystem;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand.PortcullisDirection;
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
    public void setupArmSubsystem(
            ArmSubsystem armSubsystem,
            ArmAngleMaintainerCommand armMaintain){
        armSubsystem.setDefaultCommand(armMaintain);
    }
    
    @Inject
    public void setUpPortcullisSystem(
            PortcullisWheelsSubsystem portSystem,
            SpinPortcullisWheelsCommand stop) {
        stop.setDirection(PortcullisDirection.Stop);
        portSystem.setDefaultCommand(stop);
    }
    
    @Inject
    public void setupVisionSubsystem(
            VisionSubsystem visionSubsystem,
            SpewVisionInformationCommand spewer) {
        visionSubsystem.setDefaultCommand(spewer);
    }
    
    @Inject
    public void setupHangerSubsystem(
            WinchSubsystem winchSubsystem,
            HookSubsystem hookSubsystem,
            WinchStopCommand winchStop,
            HookStopCommand hookStop){
        winchSubsystem.setDefaultCommand(winchStop);
        hookSubsystem.setDefaultCommand(hookStop);
    }
}
