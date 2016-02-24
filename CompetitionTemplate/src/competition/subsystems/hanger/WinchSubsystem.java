package competition.subsystems.hanger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class WinchSubsystem extends BaseSubsystem{
    public XSpeedController winchMotor;
    
    @Inject
    public WinchSubsystem(WPIFactory factory) {
        winchMotor = factory.getSpeedController(3);
    }
    
    public void setWinchMotorPower(double power) {
        winchMotor.set(power);
    }

}
