package competition.subsystems.shifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class ShiftingSubsystem extends BaseSubsystem {
    public final XSolenoid leftSolenoid;
    public final XSolenoid rightSolenoid;
    
    @Inject
    public ShiftingSubsystem(WPIFactory factory){
        rightSolenoid = factory.getSolenoid(1);
        leftSolenoid = factory.getSolenoid(2);
    }
    
    public void shiftHigh(){
        rightSolenoid.set(true);
        leftSolenoid.set(true);
    }
    
    public void shiftLow(){
        leftSolenoid.set(false);
        rightSolenoid.set(true);
    }
    
    
    
}
