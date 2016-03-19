package competition.subsystems.hanger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;

@Singleton
public class HookVelocityTargetSystem extends BaseSubsystem {

    private double velocityTarget;
    
    @Inject
    public HookVelocityTargetSystem() {
        
    }
    
    public double getVelocityTarget() {
        return velocityTarget;
    }
    
    public void setVelocityTarget(double target) {
        velocityTarget = target;
    }
}
