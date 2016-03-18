package competition.operator_interface;

import xbot.common.controls.sensors.AnalogHIDButton.AnalogHIDDescription;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import competition.defense_commands.ChevalCommandGroup;
import competition.defense_commands.ChevalCommandThatNeverStops;
import competition.subsystems.arm.arm_commands.ArmManualControlCommand;
import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.arm.arm_commands.ArmToDrawBridgeHeightCommand;
import competition.subsystems.arm.arm_commands.ArmToSallyPortHeightCommand;
import competition.subsystems.arm.arm_commands.ArmToScalingHeightCommand;
import competition.subsystems.arm.arm_commands.ArmToTopCommand;
import competition.subsystems.arm.arm_commands.ArmToTravelHeightCommand;
import competition.subsystems.arm.arm_commands.CalibrateArmLowCommand;
import competition.subsystems.arm.arm_commands.LowerArmCommand;
import competition.subsystems.arm.arm_commands.RaiseArmCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.autonomous.LowBarScoreCommandGroup;
import competition.subsystems.autonomous.TurnToHeadingCommand;
import competition.subsystems.autonomous.selection.DisableAutonomousCommand;
import competition.subsystems.autonomous.selection.SetupLowBarCommand;
import competition.subsystems.autonomous.selection.SetupRockWallCommand;
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
import competition.subsystems.hanger.winch_commands.winch_brake.DisengageBrakeCommand;
import competition.subsystems.hanger.winch_commands.winch_brake.EngageBrakeCommand;
import competition.subsystems.drive.commands.ResetRobotPositionCommand;
import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.collector.commands.CollectorIntakeCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand.PortcullisDirection;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.shifting.commands.ShiftLowCommand;
import competition.subsystems.vision.commands.AcquireBallCommand;
import competition.subsystems.vision.commands.CollectForwardBallCommand;
import competition.subsystems.vision.commands.RotateTowardsBallAndStopCommand;
import competition.subsystems.vision.commands.RotateTowardsBallCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristDownCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristUpCommand;

@Singleton
public class OperatorCommandMap {
    // For mapping operator interface buttons to commands

    // Example for setting up a command to fire when a button is pressed:
    
    /*
     * OI panel saftey switch mappings (physical location to button number
     * 1:10 2:11 3:12
     * 4:8 5:9 
     * Auto-pot: x-axis
     */
    
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
        
        //operatorInterface.driverGamePadButtons.getifAvailable(5).whenPressed(shiftLowCommand);
        //operatorInterface.driverGamePadButtons.getifAvailable(6).whenPressed(shiftHighCommand);
    }
    
    @Inject
    public void setupArmCommands (
            OperatorInterface operatorInterface,
            ArmToBottomCommand armToBottomCommand,
            ArmToDrawBridgeHeightCommand armToDrawBridgeHeightCommand,
            ArmToSallyPortHeightCommand armToSallyPortHeightCommand,
            ArmToScalingHeightCommand armToScalingHeightCommand,
            ArmToTravelHeightCommand armToTravelHeightCommand,
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
        
        operatorInterface.operatorPanelButtons.getifAvailable(5).whenPressed(armToBottomCommand);
        operatorInterface.operatorPanelButtons.getifAvailable(6).whenPressed(armToTravelHeightCommand);
        operatorInterface.operatorPanelButtons.getifAvailable(2).whenPressed(armToDrawBridgeHeightCommand);
        operatorInterface.operatorPanelButtons.getifAvailable(3).whenPressed(armToSallyPortHeightCommand);
        operatorInterface.operatorPanelButtons.getifAvailable(4).whenPressed(armToScalingHeightCommand);
        
        
        
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
            EngageBrakeCommand engageBrake,
            DisengageBrakeCommand disengageBrake,
            WinchFollowHookProportionallyCommand winchFollow,
            ScaleViaWinch scale){
        oi.operatorButtons.getifAvailable(9).whileHeld(hookExtend);
        oi.operatorButtons.getifAvailable(11).whileHeld(hookRetract);
        
        oi.operatorButtons.getifAvailable(10).whileHeld(winchExtend);
        oi.operatorButtons.getifAvailable(12).whileHeld(winchRetract);

        oi.rightButtons.getifAvailable(9).whenPressed(engageBrake);
        oi.rightButtons.getifAvailable(10).whenPressed(disengageBrake);
        
        //oi.leftButtons.getifAvailable(11).whileHeld(scale);
        
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
            SetupRoughDefenseForwardsCommand setupRoughDefenseCommand,
            SetupRockWallCommand setupRockWallCommand){
        driveToTurningPoint.setTargetDistance(lowBarScoreGroup.distanceFromWallToTurningPoint.get());
        driveToTurningPoint.includeOnSmartDashboard();
        
        turnToHeading.setTargetHeading(lowBarScoreGroup.headingToFaceLowGoal.get());
        turnToHeading.includeOnSmartDashboard();
        
        driveToLowGoal.setTargetDistance(lowBarScoreGroup.distanceToLowGoal.get());
        driveToLowGoal.includeOnSmartDashboard();
        
        disableAutonomousCommand.includeOnSmartDashboard();
        setupLowBarCommand.includeOnSmartDashboard();
        setupRoughDefenseCommand.includeOnSmartDashboard();
        setupRockWallCommand.includeOnSmartDashboard();
        
        oi.leftButtons.getifAvailable(8).whenPressed(setupLowBarCommand);
        oi.leftButtons.getifAvailable(9).whenPressed(setupRoughDefenseCommand);
        oi.leftButtons.getifAvailable(10).whenPressed(disableAutonomousCommand);
        oi.leftButtons.getifAvailable(7).whenPressed(setupRoughDefenseBackwardsCommand);
        oi.leftButtons.getifAvailable(11).whenPressed(setupRockWallCommand);
    }
    
    @Inject
    public void setupVisionCommands(
            OperatorInterface oi,
            RotateTowardsBallCommand rotateCommand) {
        oi.rightButtons.getifAvailable(3).whileHeld(rotateCommand);
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
}
