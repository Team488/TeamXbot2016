package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookPositionTargetSubsystem;
import xbot.common.command.BaseCommand;

public class SetHookPositionGoalCommand extends BaseCommand {

    private double positionGoal;
    private final HookPositionTargetSubsystem hookPosition;
    
    @Inject
    public SetHookPositionGoalCommand(HookPositionTargetSubsystem hookPosition) {
        this.hookPosition = hookPosition;
    }
    
    public void setTargetPosition(double target) {
        this.positionGoal = target;
    }

    @Override
    public void initialize() {
        hookPosition.setTargetPosition(positionGoal);
    }

    @Override
    public void execute() {
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
