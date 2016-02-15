package competition.subsystems.arm;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XDigitalInput;
import xbot.common.controls.sensors.XEncoder;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.PropertyManager;

@Singleton
public class ArmSubsystem extends BaseSubsystem {

    private static Logger log = Logger.getLogger(ArmSubsystem.class);
    public XSpeedController leftArmMotor;
    public XSpeedController rightArmMotor;
    public XDigitalInput upperLimitSwitch;
    public XDigitalInput lowerLimitSwitch;
    public XEncoder encoder;

    @Inject
    public ArmSubsystem(WPIFactory factory, PropertyManager propManager) {
        log.info("Creating ArmSubsystem");
        leftArmMotor = factory.getSpeedController(4);
        rightArmMotor = factory.getSpeedController(5);
        upperLimitSwitch = factory.getDigitalInput(5);
        lowerLimitSwitch = factory.getDigitalInput(6);
        encoder = factory.getEncoder(1, 2);
    }

    public boolean isArmAtMinimumHeight() {
        return lowerLimitSwitch.get();
    }

    public boolean isArmAtMaximumHeight() {
        return upperLimitSwitch.get();
    }
    
    public double getArmAngle() {
        return encoder.getDistance();
    }
    
    public void extendArm() {
        
    }
    
    public void retractArm() {
        
    }
    
    public void armMotorPower(double power) {
        leftArmMotor.set(power);
        rightArmMotor .set(power);
    }
}