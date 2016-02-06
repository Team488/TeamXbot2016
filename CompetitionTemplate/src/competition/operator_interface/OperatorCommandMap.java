package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.arm_commands.LowerArmCommand;
import competition.subsystems.arm.arm_commands.RaiseArmCommand;

@Singleton
public class OperatorCommandMap {
    // For mapping operator interface buttons to commands

    // Example for setting up a command to fire when a button is pressed:
    /*
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            MyCommand myCommand)
    {
        operatorInterface.leftButtons.getifAvailable(1).whenPressed(myCommand);
    }
    */
    
    @Inject
    public void setupArmCommands (
            OperatorInterface operatorInterface,
            RaiseArmCommand raiseArmCommand,
            LowerArmCommand lowerArmCommand)
    {
        operatorInterface.rightButtons.getifAvailable(1).whileHeld(raiseArmCommand);
        operatorInterface.leftButtons.getifAvailable(2).whileHeld(lowerArmCommand);
    }
}
