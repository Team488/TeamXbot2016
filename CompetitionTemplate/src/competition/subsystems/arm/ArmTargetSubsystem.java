package competition.subsystems.arm;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class ArmTargetSubsystem extends BaseSubsystem{
    public ArmSubsystem armSubsystem;
    double targetAngle;
    DoubleProperty withinTargetRange;
    
    @Inject
    public ArmTargetSubsystem (ArmSubsystem armSubsystem, XPropertyManager propManager){
        this.armSubsystem = armSubsystem;
        withinTargetRange = propManager.createPersistentProperty("TargetRange", 0.5);
    }
    
    public void setTargetAngle(double targetAngle){
        this.targetAngle = targetAngle;
    }
    
    public double getTargetAngle(){
        return targetAngle;
    }
    
    public boolean isWithinRange(){
        return Math.abs(armSubsystem.getArmAngle() - getTargetAngle()) <= withinTargetRange.get();
    }
}
