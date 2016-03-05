package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookSubsystem;
import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class ScaleViaWinch extends BaseCommand {

    WinchSubsystem winch;
    HookSubsystem hook;
    
    final DoubleProperty hookScalingPower;
    
    @Inject
    public ScaleViaWinch(
            WinchSubsystem winch,
            HookSubsystem hook,
            XPropertyManager propMan) {
        this.winch = winch;
        this.hook = hook;
        
        hookScalingPower = propMan.createPersistentProperty("HookScalingPower", -0.4);
    }

    @Override
    public void initialize() {        
    }

    @Override
    public void execute() {
        hook.setHookMotorPower(hookScalingPower.get());
        winch.setWinchMotorPower(-1);
    }
}
