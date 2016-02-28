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
    
    DoubleProperty armAngleDegrees;
    BooleanProperty lowerLimitSwitchProperty;
    BooleanProperty upperLimitSwitchProperty;
    
    DoubleProperty armEncoderCalibrationHeight;
    BooleanProperty armEncoderCalibrated;
    DoubleProperty armCalibrationPower;
    
    DoubleProperty armExtensionAngleDangerZoneBegin;
    DoubleProperty armExtensionAngleIllegalZoneBegin;
    
    DoubleProperty armPower;
    
    DoubleProperty upperAngleLimit;
    
    BooleanProperty enableSafeArmOperation;

    @Inject
    public ArmSubsystem(WPIFactory factory, XPropertyManager propManager) {
        log.info("Creating ArmSubsystem");
        
        leftArmMotor = factory.getSpeedController(6);        
        rightArmMotor = factory.getSpeedController(7);
        rightArmMotor.setInverted(true);
        
        upperLimitSwitch = factory.getDigitalInput(2);
        lowerLimitSwitch = factory.getDigitalInput(0);
        encoder = factory.getEncoder("ArmEncoder", 4, 5, 1.0);
        armAngleDegrees = propManager.createEphemeralProperty("armAngleDegrees", 0.0);
        lowerLimitSwitchProperty = propManager.createEphemeralProperty("armLowerLimitSwitchProperty", false);
        upperLimitSwitchProperty = propManager.createEphemeralProperty("armUpperLimitSwitchProperty", false);
        upperAngleLimit = propManager.createPersistentProperty("upperArmAngleLimit", 90.0);
               
        armEncoderCalibrationHeight = propManager.createEphemeralProperty("armEncoderCalibrationHeight", 0.0);
        armEncoderCalibrated = propManager.createEphemeralProperty("armEncoderCalibrated", false);
        armCalibrationPower = propManager.createPersistentProperty("armCalibrationPower", -0.2);
        
        armExtensionAngleDangerZoneBegin = propManager.createPersistentProperty("ArmExtensionDangerZoneBegin", 25.0);
        armExtensionAngleIllegalZoneBegin = propManager.createPersistentProperty("ArmExtensionIllegalZoneBegin", 20.0);
        
        enableSafeArmOperation = propManager.createPersistentProperty("EnableSafeArmOperation", true);
        
        armPower = propManager.createEphemeralProperty("ArmPower", 0.0);
        
        if (armExtensionAngleDangerZoneBegin.get() < armExtensionAngleIllegalZoneBegin.get()) {
            log.warn("The Illegal zone for the arm is greater than the warning zone! This may cause illegal robot behavior!!");
        }
    }

    public boolean isArmAtMinimumHeight() {
        return !lowerLimitSwitch.get();
    }

    public boolean isArmAtMaximumHeight() {
        return getArmAngle() > upperAngleLimit.get();
        //return upperLimitSwitch.get();
    }
    
    public double getArmAngle() {
        return encoder.getDistance() - armEncoderCalibrationHeight.get();
    }
    
    private double getRawArmAngle() {
        return encoder.getDistance();
    }
    

    // When the arm is low enough, extending the wrist will cause us to extend beyond the 15" legal limit.
    // This zone is where extending the wrist is still legal, but only barely. If you are in this zone,
    // this would be a good time to lower the wrist.
    public boolean isArmInDangerZone() {
        return getArmAngle() < armExtensionAngleDangerZoneBegin.get();
    }
    
    // When the arm is low enough, extending the wrist will cause us to extend beyond the 15" legal limit.
    // Commands can use this information to decide how to manipulate the wrist.
    public boolean isArmInIllegalZone() {
        return getArmAngle() < armExtensionAngleIllegalZoneBegin.get();
    }
    
    public void setArmMotorToCalibratePower() {
        setArmMotorPower(armCalibrationPower.get());
    }
    
    public void setArmMotorPower(double power) {
        if (enableSafeArmOperation.get()) {
            if (isArmAtMinimumHeight()) {
                power = Math.max(0, power);
            }
            if (isArmAtMaximumHeight()) {
                power = Math.min(power, 0);
            }
            
            setArmRawPower(power);
        }
        else {
            setArmRawPower(power);
        }
    }
    
    private void setArmRawPower(double power) {
        armPower.set(power);
        leftArmMotor.set(power);
        rightArmMotor.set(power);
    }
    
    public void calibrateCurrentPositionAsLow() {
        armEncoderCalibrationHeight.set(getRawArmAngle());
        armEncoderCalibrated.set(true);
    }
    
    public boolean isCalibrated() {
        updateSensors();
        return armEncoderCalibrated.get();
    }
    
    public void updateSensors() {
        armAngleDegrees.set(getArmAngle());
        lowerLimitSwitchProperty.set(isArmAtMinimumHeight());
        upperLimitSwitchProperty.set(isArmAtMaximumHeight());
        
        if (isArmAtMinimumHeight()) {
            calibrateCurrentPositionAsLow();
        }
    }
}