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
    
    DoubleProperty armEncoderCalibrationHeight;
    BooleanProperty armEncoderCalibrated;
    DoubleProperty armCalibrationPower;

    @Inject
    public ArmSubsystem(WPIFactory factory, XPropertyManager propManager) {
        log.info("Creating ArmSubsystem");
        leftArmMotor = factory.getSpeedController(4);
        rightArmMotor = factory.getSpeedController(5);
        upperLimitSwitch = factory.getDigitalInput(5);
        lowerLimitSwitch = factory.getDigitalInput(6);
        encoder = factory.getEncoder(1, 2);
        armAngleDegrees = propManager.createEphemeralProperty("armAngleDegrees", 0.0);
        lowerLimitSwitchProperty = propManager.createEphemeralProperty("armLowerLimitSwitchProperty", false);
        upperLimitSwitchProperty = propManager.createEphemeralProperty("armUpperLimitSwitchProperty", false);
        armEncoderDistancePerPulse = propManager.createPersistentProperty("armEncoderDistancePerPulse", 1.0);
               
        armEncoderCalibrationHeight = propManager.createEphemeralProperty("armEncoderCalibrationHeight", 0.0);
        armEncoderCalibrated = propManager.createEphemeralProperty("armEncoderCalibrated", false);
        armCalibrationPower = propManager.createPersistentProperty("armCalibrationPower", -0.2);
    }

    public boolean isArmAtMinimumHeight() {
        return lowerLimitSwitch.get();
    }

    public boolean isArmAtMaximumHeight() {
        return upperLimitSwitch.get();
    }
    
    public double getArmAngle() {
        return encoder.getDistance() * armEncoderDistancePerPulse.get() - armEncoderCalibrationHeight.get();
    }
    
    public void setArmMotorToCalibratePower() {
        setArmMotorPower(armCalibrationPower.get());
    }
    
    public void setArmMotorPower(double power) {
        leftArmMotor.set(power);
        rightArmMotor.set(power);
    }
    
    public void calibrateCurrentPositionAsLow() {
        armEncoderCalibrationHeight.set(getArmAngle());
        armEncoderCalibrated.set(true);
    }
    
    public boolean isCalibrated() {
        updateSensors();
        return armEncoderCalibrated.get();
    }
    
    public void updateSensors() {
        armAngleDegrees.set(getArmAngle());
        lowerLimitSwitchProperty.set(lowerLimitSwitch.get());
        upperLimitSwitchProperty.set(upperLimitSwitch.get());
        
        if (lowerLimitSwitch.get()) {
            calibrateCurrentPositionAsLow();
        }
    }
}