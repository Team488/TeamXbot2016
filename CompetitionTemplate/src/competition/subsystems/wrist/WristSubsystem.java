package competition.subsystems.wrist;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class WristSubsystem extends BaseSubsystem {
    public final XSolenoid solenoid;
    
    @Inject
    public WristSubsystem (WPIFactory factory) {
        solenoid = factory.getSolenoid(2);
    }
    
    public void moveWristUp() {
        solenoid.set(true);
    }
    
    public void moveWristDown() {
        solenoid.set(false);
    }

}
