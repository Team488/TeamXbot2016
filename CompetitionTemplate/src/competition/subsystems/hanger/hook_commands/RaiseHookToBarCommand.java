package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookPositionTargetSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RaiseHookToBarCommand extends SetHookPositionGoalCommand {

    private final DoubleProperty barHeight;
    
    @Inject
    public RaiseHookToBarCommand(HookPositionTargetSubsystem hookPosition, XPropertyManager propMan) {
        super(hookPosition);
        
        barHeight = propMan.createPersistentProperty("Hook-BarHeight", 50.0);
    }
    
    @Override
    public void initialize() {
        setTargetPosition(barHeight.get());
    }
}
