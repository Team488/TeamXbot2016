package competition.subsystems.autonomous.selection;

import org.apache.log4j.Logger;

import xbot.common.command.BaseCommand;
import xbot.common.command.BaseSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.StringProperty;
import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.HeadingDriveCommand;
import competition.subsystems.drive.commands.TraverseDefenseCommand;
import edu.wpi.first.wpilibj.command.Command;

@Singleton
public class AutonomousModeSelector extends BaseSubsystem {

    private static Logger log = Logger.getLogger(AutonomousModeSelector.class);
    
    public final StringProperty currentAutonomousCommandName;
    
    Command currentAutonomousCommand;

    @Inject
    public AutonomousModeSelector(XPropertyManager propManager) {
        currentAutonomousCommandName = propManager.createEphemeralProperty("currentAutonomousCommandName", "No command set");
    }
    
    public Command getCurrentAutonomousCommand() {
        return currentAutonomousCommand;
    }

    public void setCurrentAutonomousCommand(Command currentAutonomousCommand) {
        log.info("Setting CurrentAutonomousCommand to " + currentAutonomousCommand);
        if(currentAutonomousCommand != null) {
            this.currentAutonomousCommandName.set(currentAutonomousCommand.toString());
        } else {
            this.currentAutonomousCommandName.set("No command set");
        }
        this.currentAutonomousCommand = currentAutonomousCommand;
    }

}
