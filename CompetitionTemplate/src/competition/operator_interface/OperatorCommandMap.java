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
import competition.subsystems.vision.commands.RotateTowardsBallCommand;

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
        operatorInterface.leftButtons.getifAvailable(1).whenPressed(calibrateHeading);
        
        headingDrive.setTarget(PoseSubsystem.FACING_AWAY_FROM_DRIVERS);
        operatorInterface.leftButtons.getifAvailable(2).whileHeld(headingDrive);
    }
    
    @Inject
    public void setupCollectorCommands(
            OperatorInterface operatorInterface,
            CollectorIntakeCommand collectorIntakeCommand,
            CollectorEjectCommand collectorEjectCommand)
    {
        operatorInterface.rightButtons.getifAvailable(1).whileHeld(collectorIntakeCommand);
        operatorInterface.leftButtons.getifAvailable(3).whileHeld(collectorEjectCommand);
    }
    
    @Inject
    public void setupShiftingCommands(
            OperatorInterface operatorInterface,
            ShiftHighCommand shiftHighCommand,
            ShiftLowCommand shiftLowCommand)
    {
        operatorInterface.rightButtons.getifAvailable(3).whenPressed(shiftHighCommand);
        operatorInterface.leftButtons.getifAvailable(4).whenPressed(shiftLowCommand);
    }
    
    @Inject
    public void setupVisionCommands(
            OperatorInterface operatorInterface,
            RotateTowardsBallCommand ballCommand)
    {
        operatorInterface.rightButtons.getifAvailable(8).whileHeld(ballCommand);
    }
    
    @Inject
    public void setupArmCommands (
            OperatorInterface operatorInterface,
            RaiseArmCommand raiseArmCommand,
            LowerArmCommand lowerArmCommand,
            ArmToTopCommand armToTopCommand,
            ArmToBottomCommand armToBottomCommand)
    {
        operatorInterface.operatorButtons.getifAvailable(1).whileHeld(raiseArmCommand);
        operatorInterface.operatorButtons.getifAvailable(2).whileHeld(lowerArmCommand);
        
        operatorInterface.operatorButtons.getifAvailable(3).whenPressed(armToTopCommand);
        operatorInterface.operatorButtons.getifAvailable(4).whenPressed(armToBottomCommand);
    }
}
