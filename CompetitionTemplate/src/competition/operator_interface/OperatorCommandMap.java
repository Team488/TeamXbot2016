package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.arm.arm_commands.ArmToTopCommand;
import competition.subsystems.arm.arm_commands.LowerArmCommand;
import competition.subsystems.arm.arm_commands.RaiseArmCommand;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.CalibrateHeadingCommand;
import competition.subsystems.drive.commands.HeadingDriveCommand;

import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.collector.commands.CollectorIntakeCommand;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.shifting.commands.ShiftLowCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristDownCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristUpCommand;

@Singleton
public class OperatorCommandMap {
    // For mapping operator interface buttons to commands

    // Example for setting up a command to fire when a button is pressed:
    
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            CalibrateHeadingCommand calibrateHeading,
            HeadingDriveCommand headingDrive
            )
    {
        operatorInterface.leftButtons.getifAvailable(2).whenPressed(calibrateHeading);
        
        headingDrive.setTarget(PoseSubsystem.FACING_AWAY_FROM_DRIVERS);
        operatorInterface.rightButtons.getifAvailable(2).whileHeld(headingDrive);
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
            ArmToBottomCommand armToBottomCommand)
    {
        operatorInterface.operatorButtons.getifAvailable(10).whileHeld(raiseArmCommand);
        operatorInterface.operatorButtons.getifAvailable(12).whileHeld(lowerArmCommand);
        
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
}
