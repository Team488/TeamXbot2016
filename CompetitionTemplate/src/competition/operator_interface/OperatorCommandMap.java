package competition.operator_interface;

import xbot.common.controls.sensors.AnalogHIDButton.AnalogHIDDescription;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.arm_commands.ArmManualControlCommand;
import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.arm.arm_commands.ArmToTopCommand;
import competition.subsystems.arm.arm_commands.CalibrateArmLowCommand;
import competition.subsystems.arm.arm_commands.LowerArmCommand;
import competition.subsystems.arm.arm_commands.RaiseArmCommand;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.CalibrateHeadingCommand;
import competition.subsystems.drive.commands.DriveToWallCommand;
import competition.subsystems.drive.commands.HeadingDriveCommand;
import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.collector.commands.CollectorIntakeCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand.PortcullisDirection;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.shifting.commands.ShiftLowCommand;
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
            DriveToWallCommand driveToWall
            )
    {
        operatorInterface.leftButtons.getifAvailable(2).whenPressed(calibrateHeading);
        
        headingDrive.setTarget(PoseSubsystem.FACING_AWAY_FROM_DRIVERS);
        operatorInterface.rightButtons.getifAvailable(2).whileHeld(headingDrive);
        
        driveToWall.setDesiredDistance(50);
        operatorInterface.leftButtons.getifAvailable(3).whileHeld(driveToWall);
    }
    
    @Inject
    public void setupShiftingCommands(
            OperatorInterface operatorInterface,
            ShiftHighCommand shiftHighCommand,
            ShiftLowCommand shiftLowCommand)
    {
        operatorInterface.rightButtons.getifAvailable(1).whenPressed(shiftLowCommand);
        operatorInterface.leftButtons.getifAvailable(1).whenPressed(shiftHighCommand);
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
        operatorInterface.operatorButtons.getifAvailable(10).whileHeld(raiseArmCommand);
        operatorInterface.operatorButtons.getifAvailable(12).whileHeld(lowerArmCommand);
        
        AnalogHIDDescription yUp = new AnalogHIDDescription(1, 0.15, 1);
        AnalogHIDDescription yDown = new AnalogHIDDescription(1, -1, -.15);
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
    
    public void setupWristCommands(
            OperatorInterface operatorInterface,
            MoveWristDownCommand moveWristDown,
            MoveWristUpCommand moveWristUp){
        operatorInterface.operatorButtons.getifAvailable(5).whenPressed(moveWristUp);
        operatorInterface.operatorButtons.getifAvailable(3).whenPressed(moveWristDown);
    }
    
    public void setupPortcullisCommands(
            OperatorInterface oi,
            SpinPortcullisWheelsCommand up,
            SpinPortcullisWheelsCommand down) {
        up.setDirection(PortcullisDirection.Up);
        down.setDirection(PortcullisDirection.Down);
        
        oi.operatorButtons.getifAvailable(4).whileHeld(up);
        oi.operatorButtons.getifAvailable(6).whileHeld(down);
    }
}
