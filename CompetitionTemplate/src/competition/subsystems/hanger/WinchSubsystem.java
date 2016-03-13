package competition.subsystems.hanger;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.controls.actuators.XSpeedController;
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

    public final DoubleProperty winchDistance;
    public final DoubleProperty winchMaxSafeDistance;
    public final BooleanProperty enableSafeWinchOperation;
    
    @Inject
    public WinchSubsystem(WPIFactory factory, XPropertyManager propMan) {
        log.info("Creating WinchSubsystem");
        winchMotor = factory.getSpeedController(9);
        winchEncoder = factory.getEncoder("Winch", 12, 13, 1.0);
        winchDistance = propMan.createEphemeralProperty("WinchDistance", 0.0);
        
        winchMaxSafeDistance = propMan.createPersistentProperty("WinchMaxSafeDistance", 100.0);
        enableSafeWinchOperation = propMan.createPersistentProperty("EnableSafeWinchOperation", false);
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
    }
    
    public double getWinchDistance() {
        updateWinchSensors();
        return winchDistance.get();
    }
    
}
