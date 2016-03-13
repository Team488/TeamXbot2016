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
import competition.subsystems.hanger.hook_commands.HookStopCommand;
import competition.subsystems.hanger.winch_commands.WinchStopCommand;
import competition.subsystems.hanger.winch_commands.winch_brake.DisengageBrakeCommand;
import competition.subsystems.hanger.winch_commands.winch_brake.EngageBrakeCommand;
import competition.subsystems.hanger.winch_commands.winch_brake.WinchBrakeSubsystem;
import competition.subsystems.portcullis_wheels.PortcullisWheelsSubsystem;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand.PortcullisDirection;
import competition.subsystems.shifting.ShiftingSubsystem;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.vision.VisionSubsystem;
import competition.subsystems.vision.commands.VisionTelemetryReporterCommand;

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
            VisionTelemetryReporterCommand reporter) {
        visionSubsystem.setDefaultCommand(reporter);
    }
    
    @Inject
    public void setupWinchSubsystem(
            WinchSubsystem winchSubsystem,
            WinchStopCommand winchStop){
        winchSubsystem.setDefaultCommand(winchStop);
    }
    
    @Inject
    public void setupHookSubsystem(
            HookSubsystem hookSubsystem,
            HookStopCommand hookStop){
        hookSubsystem.setDefaultCommand(hookStop);
    }
    
    @Inject
    public void setupWinchBrake(
            WinchBrakeSubsystem winchBrakeSubsystem,
            EngageBrakeCommand engage,
            DisengageBrakeCommand disengage){
        winchBrakeSubsystem.setDefaultCommand(engage);
    }
    
}
