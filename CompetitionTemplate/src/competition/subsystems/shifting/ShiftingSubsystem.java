package competition.subsystems.shifting;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import competition.subsystems.wrist.WristSubsystem;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class ShiftingSubsystem extends BaseSubsystem {
    public final XSolenoid shiftHighSolenoid;
    public final XSolenoid shiftLowSolenoid;
    
    private static Logger log = Logger.getLogger(ShiftingSubsystem.class);
    
    @Inject
    public ShiftingSubsystem(WPIFactory factory){
        log.info("Creating ShiftingSubsystem");
        shiftHighSolenoid = factory.getSolenoid(0);
        shiftLowSolenoid = factory.getSolenoid(1);
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
