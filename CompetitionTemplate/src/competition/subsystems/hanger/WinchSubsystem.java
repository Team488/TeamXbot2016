package competition.subsystems.hanger;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class WinchSubsystem extends BaseSubsystem{
    public XSpeedController winchMotor;
    private static Logger log = Logger.getLogger(WinchSubsystem.class);
    
    @Inject
    public WinchSubsystem(WPIFactory factory) {
        log.info("Creating WinchSubsystem");
        winchMotor = factory.getSpeedController(8);
    }
    
    public void setWinchMotorPower(double power) {
        winchMotor.set(power);
    }

}
