package competition.subsystems.hanger;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XEncoder;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class HookSubsystem extends BaseSubsystem{
    
    public XSpeedController hookMotor;
    private static Logger log = Logger.getLogger(HookSubsystem.class);
    public XEncoder hookEncoder;
    DoubleProperty hookHeight;
    
    @Inject
    public HookSubsystem(WPIFactory factory, XPropertyManager propMan) {
        log.info("Creating HookSubsystem");
        hookMotor = factory.getSpeedController(9);
        hookEncoder = factory.getEncoder("Hook", 10, 11, 1.0);
        hookHeight = propMan.createEphemeralProperty("HookHeight", 0.0);
    }
    
    public void setHookMotorPower(double power) {
        hookMotor.set(power);
    }
    
    public double getHookDistance() {
        double distance = hookEncoder.getDistance();
        hookHeight.set(distance);
        return distance;
    }

}
