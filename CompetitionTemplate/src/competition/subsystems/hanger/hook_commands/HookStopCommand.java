package competition.subsystems.hanger.hook_commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;

public class HookStopCommand extends BaseCommand{
    HookSubsystem hookSubsystem;
    private static Logger log = Logger.getLogger(HookStopCommand.class);
    
    @Inject
    public HookStopCommand(HookSubsystem hookSubsystem){
        this.hookSubsystem =  hookSubsystem;
        this.requires(hookSubsystem);
    }

    @Override
    public void initialize() {
        log.info("initializing HookStopCommand");
        hookSubsystem.setHookMotorPower(0);
    }

    @Override
    public void execute() {
        
    }

}
