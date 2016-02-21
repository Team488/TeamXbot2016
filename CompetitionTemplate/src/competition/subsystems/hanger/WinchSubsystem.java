package competition.subsystems.hanger;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

public class WinchSubsystem extends BaseSubsystem{
public XSpeedController winchMotor;
    
    public WinchSubsystem(WPIFactory factory) {
        winchMotor = factory.getSpeedController(2);
    }
    
    public void setWinchMotorPower(double power) {
        winchMotor.set(power);
    }

}
