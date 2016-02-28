package competition.subsystems.hanger.hook_commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class HookRetractCommand extends BaseCommand{
    public HookSubsystem hookSubsystem;
    DoubleProperty hookRetractionPower;
    private static Logger log = Logger.getLogger(HookRetractCommand.class);
    
    @Inject
    public HookRetractCommand(HookSubsystem hookSubsystem, XPropertyManager propManager){
        this.hookSubsystem = hookSubsystem;
        hookRetractionPower = propManager.createPersistentProperty("hook retraction power", -1.0);
        this.requires(hookSubsystem);
    }

    @Override
    public void initialize(){
        log.info("initializing HookRetractCommand");
        hookSubsystem.setHookMotorPower(hookRetractionPower.get());
    }

    @Override
    public void execute(){
        
    }
    
    public void end(){
        hookSubsystem.setHookMotorPower(0);
    }


}
