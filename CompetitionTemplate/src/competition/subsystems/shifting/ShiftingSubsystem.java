package competition.subsystems.shifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class ShiftingSubsystem extends BaseSubsystem {
    public final XSolenoid shiftHighSolenoid;
    public final XSolenoid shiftLowSolenoid;
    
    @Inject
    public ShiftingSubsystem(WPIFactory factory){
        shiftHighSolenoid = factory.getSolenoid(1);
        shiftLowSolenoid = factory.getSolenoid(2);
    }
    
    public void shiftHigh(){
        shiftHighSolenoid.set(true);
        shiftLowSolenoid.set(false);
    }
    
    public void shiftLow(){
        shiftHighSolenoid.set(false);
        shiftLowSolenoid.set(true);
    }
    
    
    
}
