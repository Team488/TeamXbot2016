package competition.subsystems.wrist;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class WristSubsystem extends BaseSubsystem {
    public final XSolenoid solenoidA;
    
    private static Logger log = Logger.getLogger(WristSubsystem.class);
    
    @Inject
    public WristSubsystem (WPIFactory factory) {
        log.info("Creating WristSubsystem");
        solenoidA = factory.getSolenoid(0);
    }
    
    public void moveWristUp() {
        solenoidA.set(false);
    }
    
    public void moveWristDown() {
        solenoidA.set(true);
    }

}
