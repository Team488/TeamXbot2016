package competition.subsystems.hanger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;

@Singleton
public class HookPositionTargetSubsystem extends BaseSubsystem {

    private double targetPosition;
    
    @Inject
    public HookPositionTargetSubsystem() {
        
    }
    
    public double getTargetPosition() {
        return targetPosition;
    }
    
    public void setTargetPosition(double target) {
        targetPosition = target;
    }
}
