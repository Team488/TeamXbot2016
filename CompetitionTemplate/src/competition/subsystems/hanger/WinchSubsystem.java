package competition.subsystems.hanger;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XDigitalInput;
import xbot.common.controls.sensors.XEncoder;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class WinchSubsystem extends BaseSubsystem{
    public XSpeedController winchMotor;
    private static Logger log = Logger.getLogger(WinchSubsystem.class);
    public XEncoder winchEncoder;
    public XDigitalInput hallEffect;

    public final DoubleProperty winchDistance;
    public final DoubleProperty winchMaxSafeDistance;
    public final BooleanProperty enableSafeWinchOperation;
    public final BooleanProperty winchAtLimit;
    
    @Inject
    public WinchSubsystem(WPIFactory factory, XPropertyManager propMan) {
        log.info("Creating WinchSubsystem");
        winchMotor = factory.getSpeedController(9);
        winchEncoder = factory.getEncoder("Winch", 2, 3, 1.0);
        winchDistance = propMan.createEphemeralProperty("WinchDistance", 0.0);
        hallEffect = factory.getDigitalInput(16);
        
        winchMaxSafeDistance = propMan.createPersistentProperty("WinchMaxSafeDistance", 100.0);
        enableSafeWinchOperation = propMan.createPersistentProperty("EnableSafeWinchOperation", false);
        winchAtLimit = propMan.createEphemeralProperty("WinchAtLimit", false);
    }
    
    public void setWinchMotorPower(double power) {
        if (enableSafeWinchOperation.get()) {
            if (getWinchDistance() > winchMaxSafeDistance.get()) {
                // winch fully extended - only allow retraction!
                power = Math.min(0, power);
            }
            if (getWinchDistance() <= 0) {
                power = Math.max(0, power);
            }
        }
        
        winchMotor.set(power);
    }
    
    public void updateWinchSensors() {
        double distance = winchEncoder.getDistance();
        winchDistance.set(distance);
        
        winchAtLimit.set(hallEffect.get());
    }
    
    public double getWinchDistance() {
        updateWinchSensors();
        return winchDistance.get();
    }
    
}
