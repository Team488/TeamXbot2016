package competition.subsystems.wrist;

import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

public class WristSubsystem {
    public final XSolenoid solenoid;
    
    public WristSubsystem (WPIFactory factory){
        solenoid = factory.getSolenoid(2);
    }
    
    public void moveWristUp(){
        solenoid.set(true);
    }
    
    public void moveWristDown(){
        solenoid.set(false);
    }

}
