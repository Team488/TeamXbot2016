package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;

public class UpdateHookSensorsCommand extends BaseCommand {
    HookSubsystem hookSubsystem;
    
    @Inject
    public UpdateHookSensorsCommand(HookSubsystem hookSubsystem) {
        this.hookSubsystem = hookSubsystem;
        this.setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        hookSubsystem.getHookDistance();
    }

}
