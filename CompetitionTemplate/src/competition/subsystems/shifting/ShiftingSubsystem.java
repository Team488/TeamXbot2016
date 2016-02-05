package competition.subsystems.shifting;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;


public class ShiftingSubsystem extends BaseSubsystem {
    public final XSolenoid solenoid;
    
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
