package competition.subsystems.arm;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XDigitalInput;
import xbot.common.controls.sensors.XEncoder;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class ArmSubsystem extends BaseSubsystem {

    private static Logger log = Logger.getLogger(ArmSubsystem.class);
    public XSpeedController leftArmMotor;
    public XSpeedController rightArmMotor;
    public XDigitalInput upperLimitSwitch;
    public XDigitalInput lowerLimitSwitch;
    public XEncoder encoder;
    
    DoubleProperty armEncoderDistancePerPulse;
    DoubleProperty armAngleDegrees;
    BooleanProperty lowerLimitSwitchProperty;
    BooleanProperty upperLimitSwitchProperty;

    @Inject
    public ArmSubsystem(WPIFactory factory, XPropertyManager propManager) {
        log.info("Creating ArmSubsystem");
        leftArmMotor = factory.getSpeedController(4);
        rightArmMotor = factory.getSpeedController(5);
        
        upperLimitSwitch = factory.getDigitalInput(1);
        lowerLimitSwitch = factory.getDigitalInput(2);
        encoder = factory.getEncoder(4, 5);
        armAngleDegrees = propManager.createEphemeralProperty("armAngleDegrees", 0.0);
        lowerLimitSwitchProperty = propManager.createEphemeralProperty("armLowerLimitSwitchProperty", false);
        upperLimitSwitchProperty = propManager.createEphemeralProperty("armUpperLimitSwitchProperty", false);
        armEncoderDistancePerPulse = propManager.createPersistentProperty("armEncoderDistancePerPulse", 1.0);
        encoder.setDistancePerPulse(armEncoderDistancePerPulse.get());
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
    
    public void setArmMotorPower(double power) {
        leftArmMotor.set(power);
        rightArmMotor.set(power);
    }
    
    public void updateSensors() {
        armAngleDegrees.set(getArmAngle());
        lowerLimitSwitchProperty.set(lowerLimitSwitch.get());
        upperLimitSwitchProperty.set(upperLimitSwitch.get());
        
    }
}