package competition.subsystems.arm;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.PropertyManager;

@Singleton
public class ArmSubsystem extends BaseSubsystem {

    private static Logger log = Logger.getLogger(ArmSubsystem.class);
    public XSpeedController armMotor;

    @Inject
    public ArmSubsystem(WPIFactory factory, PropertyManager propManager) {
        log.info("Creating ArmSubsystem");
        armMotor = factory.getSpeedController(4);
    }

    public boolean isArmAtMinimumHeight() {
        return false;
    }

    public boolean isArmAtMaximumHeight() {
        return false;
    }
    
    public double getArmHeight() {
        return 0;
    }

    public void setArmHeight(double normalizedHeight) {

    }
    
    public void extendArm() {
        
    }
    
    public void retractArm() {
        
    }
}