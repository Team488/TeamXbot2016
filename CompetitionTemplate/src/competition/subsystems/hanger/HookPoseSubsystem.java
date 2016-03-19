package competition.subsystems.hanger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.Timer;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.sensors.XEncoder;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class HookPoseSubsystem extends BaseSubsystem {

    public final XEncoder hookEncoder;
    final DoubleProperty hookHeight;
    final DoubleProperty hookVelocity;
        
    @Inject
    public HookPoseSubsystem(WPIFactory factory, XPropertyManager propMan) {
        hookEncoder = factory.getEncoder("Hook", 10, 11, 1.0);
        hookEncoder.setInverted(true);
        hookHeight = propMan.createEphemeralProperty("HookHeight", 0.0);
        hookVelocity = propMan.createEphemeralProperty("HookVelocity", 0.0);
    }
    
    public void updateHookSensors() {
        double distance = hookEncoder.getDistance();
        hookHeight.set(distance);
        
        // try this first before getting complicated
        hookVelocity.set(hookEncoder.getRate());
    }
    
    public double getHookDistance() {
        updateHookSensors();
        return hookHeight.get();
    }
    
    public double getHookVelocity() {
        updateHookSensors();
        return hookVelocity.get();
    }
}
