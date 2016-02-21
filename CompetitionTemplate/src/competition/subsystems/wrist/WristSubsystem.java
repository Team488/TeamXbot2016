package competition.subsystems.wrist;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import competition.subsystems.drive.DriveSubsystem;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class WristSubsystem extends BaseSubsystem {
    public final XSolenoid solenoidA;
    public final XSolenoid solenoidB;
    
    private static Logger log = Logger.getLogger(WristSubsystem.class);
    
    @Inject
    public WristSubsystem (WPIFactory factory) {
    	log.info("Creating WristSubsystem");
        solenoidA = factory.getSolenoid(2);
        solenoidB = factory.getSolenoid(3);
    }
    
    public void moveWristUp() {
        solenoidA.set(true);
        solenoidB.set(false);
    }
    
    public void moveWristDown() {
        solenoidA.set(false);
        solenoidB.set(true);
    }

}
