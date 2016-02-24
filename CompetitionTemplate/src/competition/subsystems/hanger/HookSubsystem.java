package competition.subsystems.hanger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class HookSubsystem extends BaseSubsystem{
    public XSpeedController hookMotor;
    
    @Inject
    public HookSubsystem(WPIFactory factory) {
        hookMotor = factory.getSpeedController(2);
    }
    
    public void setHookMotorPower(double power) {
        hookMotor.set(power);
    }

}
