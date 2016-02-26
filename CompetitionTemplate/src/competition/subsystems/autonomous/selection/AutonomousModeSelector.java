package competition.subsystems.autonomous.selection;

import org.apache.log4j.Logger;

import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.HeadingDriveCommand;
import competition.subsystems.drive.commands.TraverseDefenseCommand;

@Singleton
public class AutonomousModeSelector {

    private static Logger log = Logger.getLogger(AutonomousModeSelector.class);
    
    BaseCommand currentAutonomousCommand;

    public BaseCommand getCurrentAutonomousCommand() {
        return currentAutonomousCommand;
    }

    public void setCurrentAutonomousCommand(BaseCommand currentAutonomousCommand) {
        log.info("Setting CurrentAutonomousCommand to " + currentAutonomousCommand);
        this.currentAutonomousCommand = currentAutonomousCommand;
    }
    
    @Inject
    public AutonomousModeSelector() {

    }
}
