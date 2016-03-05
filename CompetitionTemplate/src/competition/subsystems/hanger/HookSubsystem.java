package competition.subsystems.hanger;
import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XEncoder;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class HookSubsystem extends BaseSubsystem{
    
    public XSpeedController hookMotor;
    private static Logger log = Logger.getLogger(HookSubsystem.class);
    public XEncoder hookEncoder;
    final DoubleProperty hookHeight;
    final DoubleProperty maxSafeHookHeight;
    final BooleanProperty EnableSafeHookOperation;
    
    @Inject
    public HookSubsystem(WPIFactory factory, XPropertyManager propMan) {
        log.info("Creating HookSubsystem");
        hookMotor = factory.getSpeedController(8);
        hookEncoder = factory.getEncoder("Hook", 10, 11, 1.0);
        
        hookHeight = propMan.createEphemeralProperty("HookHeight", 0.0);
        maxSafeHookHeight = propMan.createPersistentProperty("MaxSafeHookHeight", 100.0);
        
        EnableSafeHookOperation = propMan.createPersistentProperty("EnableSafeHookOperation", false);
    }
    
    public void setHookMotorPower(double power) {
        if (EnableSafeHookOperation.get()) {
            if (getHookDistance() > maxSafeHookHeight.get()) {
                // We're at the max safe extension - only allow negative (retracting) power
                power = Math.min(power, 0);
            }
            if (getHookDistance() < 0) {
                // We're all the way at the bottom - don't try to pull any more!
                power = Math.max(power, 0);
            }
        }
        
        hookMotor.set(power);
    }
    
    public void updateHookSensors() {
        double distance = hookEncoder.getDistance();
        hookHeight.set(distance);
    }
    
    public double getHookDistance() {
        updateHookSensors();
        return hookHeight.get();
    }

}
