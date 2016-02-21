package competition.subsystems.hanger;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

public class HookSubsystem extends BaseSubsystem{
    public XSpeedController hookMotor;
    
    public HookSubsystem(WPIFactory factory) {
        hookMotor = factory.getSpeedController(2);
    }
    
    public void setHookMotorPower(double power) {
        hookMotor.set(power);
    }

}
