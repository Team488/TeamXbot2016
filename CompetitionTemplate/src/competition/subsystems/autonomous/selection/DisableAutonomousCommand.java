package competition.subsystems.autonomous.selection;

import com.google.inject.Inject;

public class DisableAutonomousCommand extends BaseAutonomousModeSetCommand {

    @Inject
    public DisableAutonomousCommand(AutonomousModeSelector autonomousModeSelector) {
        super(autonomousModeSelector);
    }
    
    @Override
    public void initialize() {
        // set null, causing no program to be run
        autonomousModeSelector.setCurrentAutonomousCommand(null);
    }

}
