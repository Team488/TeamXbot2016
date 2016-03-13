package competition.operator_interface;

import xbot.common.controls.sensors.AnalogHIDButton.AnalogHIDDescription;

import com.google.inject.Inject;
import com.google.inject.Singleton;


import competition.subsystems.arm.ArmSubsystem;

import competition.defense_commands.ChevalCommandGroup;
import competition.defense_commands.ChevalCommandThatNeverStops;
import competition.subsystems.arm.arm_commands.ArmManualControlCommand;
import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.arm.arm_commands.ArmToTopCommand;
import competition.subsystems.arm.arm_commands.CalibrateArmLowCommand;
import competition.subsystems.arm.arm_commands.DisableSafeArmOperationCommand;
import competition.subsystems.arm.arm_commands.LowerArmCommand;
import competition.subsystems.arm.arm_commands.RaiseArmCommand;
import competition.subsystems.autonomous.LowBarScoreCommandGroup;
import competition.subsystems.autonomous.TurnToHeadingCommand;
import competition.subsystems.autonomous.selection.DisableAutonomousCommand;
import competition.subsystems.autonomous.selection.SetupLowBarCommand;
import competition.subsystems.autonomous.selection.SetupRoughDefenseBackwardsCommand;
import competition.subsystems.autonomous.selection.SetupRoughDefenseForwardsCommand;
import competition.subsystems.drive.commands.CalibrateHeadingCommand;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import competition.subsystems.drive.commands.CalibrateInherentRioRotationCommand;
import competition.subsystems.drive.commands.DriveToWallCommand;
import competition.subsystems.drive.commands.HeadingDriveCommand;
import competition.subsystems.hanger.hook_commands.HookExtendCommand;
import competition.subsystems.hanger.hook_commands.HookRetractCommand;
import competition.subsystems.hanger.winch_commands.ScaleViaWinch;
import competition.subsystems.hanger.winch_commands.WinchExtendCommand;
import competition.subsystems.hanger.winch_commands.WinchFollowHookProportionallyCommand;
import competition.subsystems.hanger.winch_commands.WinchRetractCommand;
import competition.subsystems.drive.commands.ResetRobotPositionCommand;
import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.collector.commands.CollectorIntakeCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand.PortcullisDirection;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.shifting.commands.ShiftLowCommand;
import competition.subsystems.vision.commands.AcquireBallCommand;
import competition.subsystems.vision.commands.CollectForwardBallCommand;
import competition.subsystems.vision.commands.RotateTowardsBallCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristDownCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristUpCommand;

@Singleton
public class OperatorCommandMap {
    // For mapping operator interface buttons to commands

    // Example for setting up a command to fire when a button is pressed:
    
    @Inject
    public void setupDriveCommands(
            OperatorInterface operatorInterface,
            CalibrateHeadingCommand calibrateHeading,
            HeadingDriveCommand headingDrive,
            DriveToWallCommand driveToWall,
            ResetRobotPositionCommand resetPosition,
            ChevalCommandThatNeverStops cheval
            )
    {
        operatorInterface.leftButtons.getifAvailable(2).whenPressed(calibrateHeading);
        
        resetPosition.includeOnSmartDashboard("Reset Position");
        
        operatorInterface.leftButtons.getifAvailable(3).whileHeld(cheval);
    }
    
    @Inject
    public void setupShiftingCommands(
            OperatorInterface operatorInterface,
            ShiftHighCommand shiftHighCommand,
            ShiftLowCommand shiftLowCommand)
    {
        operatorInterface.rightButtons.getifAvailable(1).whenPressed(shiftLowCommand);
        operatorInterface.leftButtons.getifAvailable(1).whenPressed(shiftHighCommand);
        
        operatorInterface.driverGamePadButtons.getifAvailable(5).whenPressed(shiftLowCommand);
        operatorInterface.driverGamePadButtons.getifAvailable(6).whenPressed(shiftHighCommand);
    }
    
    @Inject
    public void setupArmCommands (
            OperatorInterface operatorInterface,
            RaiseArmCommand raiseArmCommand,
            LowerArmCommand lowerArmCommand,
            ArmToTopCommand armToTopCommand,
            ArmToBottomCommand armToBottomCommand,
            ArmManualControlCommand armManual,
            CalibrateArmLowCommand calibrateArmLow)
    {
        double minValue = 0.15;
        AnalogHIDDescription yUp = new AnalogHIDDescription(1, minValue, 1);
        AnalogHIDDescription yDown = new AnalogHIDDescription(1, -1, -minValue);
        operatorInterface.operatorButtons.addAnalogButton(yUp);
        operatorInterface.operatorButtons.addAnalogButton(yDown);
        
        operatorInterface.operatorButtons.getAnalogIfAvailable(yUp).whileHeld(armManual);
        operatorInterface.operatorButtons.getAnalogIfAvailable(yDown).whileHeld(armManual);
        
        operatorInterface.operatorButtons.getifAvailable(7).whenPressed(calibrateArmLow);
        
//        operatorInterface.operatorButtons.getifAvailable(3).whenPressed(armToTopCommand);
//        operatorInterface.operatorButtons.getifAvailable(4).whenPressed(armToBottomCommand);
    }
    
    @Inject
    public void setupCollectorCommands(
            OperatorInterface operatorInterface,
            CollectorIntakeCommand collectorIntakeCommand,
            CollectorEjectCommand collectorEjectCommand)
    {
        operatorInterface.operatorButtons.getifAvailable(1).whileHeld(collectorIntakeCommand);
        operatorInterface.operatorButtons.getifAvailable(2).whileHeld(collectorEjectCommand);
    }
    
    @Inject
    public void setupWristCommands(
            OperatorInterface operatorInterface,
            MoveWristDownCommand moveWristDown,
            MoveWristUpCommand moveWristUp){
        operatorInterface.operatorButtons.getifAvailable(5).whenPressed(moveWristUp);
        operatorInterface.operatorButtons.getifAvailable(3).whenPressed(moveWristDown);
    }
    
    @Inject
    public void setupPortcullisCommands(
            OperatorInterface oi,
            SpinPortcullisWheelsCommand up,
            SpinPortcullisWheelsCommand down) {
        up.setDirection(PortcullisDirection.Up);
        down.setDirection(PortcullisDirection.Down);
        
        oi.operatorButtons.getifAvailable(4).whileHeld(up);
        oi.operatorButtons.getifAvailable(6).whileHeld(down);
    }
    
    @Inject
    public void setupHangerCommands(
            OperatorInterface oi,
            HookExtendCommand hookExtend,
            HookRetractCommand hookRetract,
            WinchExtendCommand winchExtend,
            WinchRetractCommand winchRetract,
            WinchFollowHookProportionallyCommand winchFollow,
            ScaleViaWinch scale){
        oi.operatorButtons.getifAvailable(9).whileHeld(hookExtend);
        oi.operatorButtons.getifAvailable(11).whileHeld(hookRetract);
        
        oi.operatorButtons.getifAvailable(10).whileHeld(winchExtend);
        oi.operatorButtons.getifAvailable(12).whileHeld(winchRetract);
        
        oi.leftButtons.getifAvailable(11).whileHeld(scale);
        
        winchFollow.includeOnSmartDashboard();
        scale.includeOnSmartDashboard();
    }
    
    @Inject
    public void setupAutonomousCommands(
            OperatorInterface oi,
            DriveToDistanceCommand driveToTurningPoint,
            DriveToDistanceCommand driveToLowGoal,
            LowBarScoreCommandGroup lowBarScoreGroup,
            TurnToHeadingCommand turnToHeading,
            DisableAutonomousCommand disableAutonomousCommand,
            SetupLowBarCommand setupLowBarCommand,
            SetupRoughDefenseBackwardsCommand setupRoughDefenseBackwardsCommand,
            SetupRoughDefenseForwardsCommand setupRoughDefenseCommand){
        driveToTurningPoint.setTargetDistance(lowBarScoreGroup.distanceToTurningPoint.get());
        driveToTurningPoint.includeOnSmartDashboard();
        
        turnToHeading.setTargetHeading(lowBarScoreGroup.targetHeading.get());
        turnToHeading.includeOnSmartDashboard();
        
        driveToLowGoal.setTargetDistance(lowBarScoreGroup.distanceToLowGoal.get());
        driveToLowGoal.includeOnSmartDashboard();
        
        disableAutonomousCommand.includeOnSmartDashboard();
        setupLowBarCommand.includeOnSmartDashboard();
        setupRoughDefenseCommand.includeOnSmartDashboard();
        
        oi.leftButtons.getifAvailable(8).whenPressed(setupLowBarCommand);
        oi.leftButtons.getifAvailable(9).whenPressed(setupRoughDefenseCommand);
        oi.leftButtons.getifAvailable(10).whenPressed(disableAutonomousCommand);
        oi.leftButtons.getifAvailable(7).whenPressed(setupRoughDefenseBackwardsCommand);
    }
    
    @Inject
    public void setupVisionCommands(
            OperatorInterface oi,
            AcquireBallCommand acquireCommand) {
        //oi.rightButtons.getifAvailable(3).whileHeld(acquireCommand);
    }
    
    @Inject
    public void setupDashboard(
            RotateTowardsBallCommand rotate, 
            CollectForwardBallCommand collect, 
            AcquireBallCommand acquire,
            CalibrateInherentRioRotationCommand rioRotationCalibration) {
        rotate.includeOnSmartDashboard();
        collect.includeOnSmartDashboard();
        acquire.includeOnSmartDashboard();
        rioRotationCalibration.includeOnSmartDashboard();
    }
    
    @Inject
    public void switchEnableSafeArmOperation(
            OperatorInterface oi,
            ArmSubsystem arm,
            DisableSafeArmOperationCommand disableSafeArm){
        oi.operatorPanelButtons.getifAvailable(1).whileHeld(disableSafeArm);
    }
}
