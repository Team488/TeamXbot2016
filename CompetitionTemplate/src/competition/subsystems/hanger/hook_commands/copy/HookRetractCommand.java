package competition.subsystems.hanger.hook_commands.copy;

import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class HookRetractCommand extends BaseCommand{
    public HookSubsystem hookSubsystem;
    DoubleProperty hookRetractionPower;
    
    public HookRetractCommand(HookSubsystem hookSubsystem, XPropertyManager propManager){
        this.hookSubsystem = hookSubsystem;
        hookRetractionPower = propManager.createPersistentProperty("hook retraction power", 1.0);
    }

    @Override
    public void initialize(){
        hookSubsystem.setHookMotorPower(hookRetractionPower.get());
    }

    @Override
    public void execute(){
        
    }
    
    public void end(){
        hookSubsystem.setHookMotorPower(0);
    }


}
