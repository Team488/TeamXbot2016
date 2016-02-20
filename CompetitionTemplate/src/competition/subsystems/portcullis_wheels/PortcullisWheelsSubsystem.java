package competition.subsystems.portcullis_wheels;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class PortcullisWheelsSubsystem extends BaseSubsystem {
        
    private static Logger log = Logger.getLogger(PortcullisWheelsSubsystem.class);

    public XSpeedController portcullisWheel;
    
    @Inject
    public PortcullisWheelsSubsystem(WPIFactory factory, XPropertyManager propMan) {       
        portcullisWheel = factory.getSpeedController(6);
    }
    
    public void setWheelSpeed(double power) {
        portcullisWheel.set(power);
    }
    
}