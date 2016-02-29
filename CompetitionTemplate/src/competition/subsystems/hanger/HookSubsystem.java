package competition.subsystems.hanger;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class HookSubsystem extends BaseSubsystem{
    public XSpeedController hookMotor;
    private static Logger log = Logger.getLogger(HookSubsystem.class);
    
    @Inject
    public HookSubsystem(WPIFactory factory) {
        log.info("Creating HookSubsystem");
        hookMotor = factory.getSpeedController(8);
    }
    
    public void setHookMotorPower(double power) {
        hookMotor.set(power);
    }

}
