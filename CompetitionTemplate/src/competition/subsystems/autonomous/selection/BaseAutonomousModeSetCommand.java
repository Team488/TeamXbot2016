package competition.subsystems.autonomous.selection;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;

public abstract class BaseAutonomousModeSetCommand extends BaseCommand {
    
    final AutonomousModeSelector autonomousModeSelector;
    
    @Inject
    public BaseAutonomousModeSetCommand(AutonomousModeSelector autonomousModeSelector) {
        this.autonomousModeSelector = autonomousModeSelector;
        this.requires(this.autonomousModeSelector);
        this.setRunWhenDisabled(true);
    }
    
    @Override
    public void execute() {
        // no-op, all work should be done in initialize of subclasses
    }

    @Override
    public boolean isFinished() {
        // All of these commands should run exactly once.
        return true;
    }
}
