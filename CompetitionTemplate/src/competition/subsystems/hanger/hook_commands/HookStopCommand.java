package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;

public class HookStopCommand extends BaseCommand{
    HookSubsystem hookSubsystem;
    
    @Inject
    public HookStopCommand(HookSubsystem hookSubsystem){
        this.hookSubsystem =  hookSubsystem;
        this.requires(hookSubsystem);
    }

    @Override
    public void initialize() {
        hookSubsystem.setHookMotorPower(0);
    }

    @Override
    public void execute() {
        
    }

}
