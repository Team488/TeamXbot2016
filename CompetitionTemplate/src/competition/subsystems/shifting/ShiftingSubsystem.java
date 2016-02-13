package competition.subsystems.shifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class ShiftingSubsystem extends BaseSubsystem {
    public final XSolenoid solenoid;
    
    @Inject
    public ShiftingSubsystem(WPIFactory factory){
        solenoid = factory.getSolenoid(1);
    }
    
    public void shiftHigh(){
        solenoid.set(true);
    }
    
    public void shiftLow(){
        solenoid.set(false);
    }
    
    
    
}
