package competition.subsystems.arm;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;

@Singleton
public class ArmTargetSubsystem extends BaseSubsystem{
    double targetAngle;
    
    @Inject
    public ArmTargetSubsystem (){
        
    }
    
    public double setTargetAngle(double targetAngle){
        this.targetAngle = targetAngle;
        return targetAngle;
    }
    
    public double getTargetAngle(){
        return targetAngle;
    }
}
