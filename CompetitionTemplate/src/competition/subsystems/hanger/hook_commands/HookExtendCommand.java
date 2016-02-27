package competition.subsystems.hanger.hook_commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class HookExtendCommand extends BaseCommand{
    HookSubsystem hookSubsystem;
    public DoubleProperty hookExtentionPower;
    private static Logger log = Logger.getLogger(HookExtendCommand.class);
    
    @Inject
    public HookExtendCommand(HookSubsystem hookSubsystem, XPropertyManager propManager){
        this.hookSubsystem = hookSubsystem;
        hookExtentionPower = propManager.createPersistentProperty("hook extention power", 1.0);
        this.requires(hookSubsystem);
    }

    @Override
    public void initialize(){
        log.info("initializing HookExtendCommand");
        hookSubsystem.setHookMotorPower(hookExtentionPower.get());
    }

    @Override
    public void execute(){
        
    }
    
    public void end(){
        hookSubsystem.setHookMotorPower(0);
    }

}
