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
public class WinchSubsystem extends BaseSubsystem{
    public XSpeedController winchMotor;
    private static Logger log = Logger.getLogger(WinchSubsystem.class);
    public XEncoder winchEncoder;
    public DoubleProperty winchDistance;
    
    @Inject
    public WinchSubsystem(WPIFactory factory, XPropertyManager propMan) {
        log.info("Creating WinchSubsystem");
        winchMotor = factory.getSpeedController(8);
        winchEncoder = factory.getEncoder("Winch", 12, 13, 1.0);
        winchDistance = propMan.createEphemeralProperty("WinchDistance", 0.0);
    }
    
    public void setWinchMotorPower(double power) {
        winchMotor.set(power);
    }
    
    public double getWinchDistance() {
        double distance = winchEncoder.getDistance();
        winchDistance.set(distance);
        return distance;
    }

}
