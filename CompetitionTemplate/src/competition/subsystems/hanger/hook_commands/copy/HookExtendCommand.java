package competition.subsystems.hanger.hook_commands.copy;

import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class HookExtendCommand extends BaseCommand{
    public HookSubsystem hookSubsystem;
    DoubleProperty hookExtentionPower;
    
    public HookExtendCommand(HookSubsystem hookSubsystem, XPropertyManager propManager){
        this.hookSubsystem = hookSubsystem;
        hookExtentionPower = propManager.createPersistentProperty("hook extention power", -1.0);
    }

    @Override
    public void initialize(){
        hookSubsystem.setHookMotorPower(hookExtentionPower.get());
    }

    @Override
    public void execute(){
        
    }
    
    public void end(){
        hookSubsystem.setHookMotorPower(0);
    }

}
