package competition.subsystems.autonomous.selection;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;

public abstract class BaseAutonomousModeSetCommand extends BaseCommand {
    final AutonomousModeSelector autonomousModeSelector;
    
    @Inject
    public BaseAutonomousModeSetCommand(AutonomousModeSelector autonomousModeSelector) {
        this.autonomousModeSelector = autonomousModeSelector;
    }

    @Override
    public void execute() {
        // no-op, all work done in initialize
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
