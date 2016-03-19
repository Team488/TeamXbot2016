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
    final DoubleProperty maxSafeHookHeight;
    final BooleanProperty enableSafeHookOperation;
    
    HookPoseSubsystem hookPose;
    
    @Inject
    public HookSubsystem(WPIFactory factory, XPropertyManager propMan, HookPoseSubsystem hookPose) {
        this.hookPose = hookPose;
        
        log.info("Creating HookSubsystem");
        hookMotor = factory.getSpeedController(8);        
        hookMotor.setInverted(true);
        
        maxSafeHookHeight = propMan.createPersistentProperty("MaxSafeHookHeight", 100.0);
        enableSafeHookOperation = propMan.createPersistentProperty("EnableSafeHookOperation", false);
    }
    
    public void setHookMotorPower(double power) {
        if (enableSafeHookOperation.get()) {
            if (hookPose.getHookDistance() > maxSafeHookHeight.get()) {
                // We're at the max safe extension - only allow negative (retracting) power
                power = Math.min(power, 0);
            }
            if (hookPose.getHookDistance() < 0) {
                // We're all the way at the bottom - don't try to pull any more!
                power = Math.max(power, 0);
            }
        }
        
        hookMotor.set(power);
    }
    

}
