package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.CalibrateHeadingCommand;
import competition.subsystems.drive.commands.HeadingDriveCommand;

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
    
}
