package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookPositionTargetSubsystem;
import xbot.common.command.BaseCommand;

public class ModifyHookTargetGoalCommand extends BaseCommand {

    HookPositionTargetSubsystem hookPosition;
    private double incrementValue;
    
    @Inject
    public ModifyHookTargetGoalCommand(HookPositionTargetSubsystem hookPosition) {
        this.hookPosition = hookPosition;
    }
    
    public void setIncrement(double increment) {
        incrementValue = increment;
    }
    
    @Override
    public void initialize() {
        double currentGoal = hookPosition.getTargetPosition();
        hookPosition.setTargetPosition(currentGoal + incrementValue);
    }

    @Override
    public void execute() {
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
