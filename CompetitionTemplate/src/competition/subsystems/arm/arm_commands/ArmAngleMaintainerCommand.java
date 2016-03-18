package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import edu.wpi.first.wpilibj.Timer;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class ArmAngleMaintainerCommand extends BaseCommand{
    public ArmSubsystem armSubsystem;
    public ArmTargetSubsystem armTargetSubsystem;
    double armPower;
    AttemptCalibrationState calibration;
    BooleanProperty autoCalibrationEnabled;
    DoubleProperty autoCalibrationTimeout;
    BooleanProperty gaveUpCalibrating;
    
    DoubleProperty kickstandRaiseAngle;
    DoubleProperty kickstandRaisePower;
    DoubleProperty kickstandRaiseDuration;
    
    double beginCalibrationTime = 0;
    
    boolean oldIsCalibrated = false;
    
    PIDManager pidManager;
    
    public enum AttemptCalibrationState {
        NotCalibrated,
        RaiseArmToLowerKickstand,
        WaitForArmToLower,
        AbortCalibration
    }
    
    public void setAutoCalibration(boolean value) {
        autoCalibrationEnabled.set(value);
    }
    
    @Inject
    public ArmAngleMaintainerCommand(ArmSubsystem armSubsystem, ArmTargetSubsystem armTargetSubsystem,
            XPropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        this.armTargetSubsystem = armTargetSubsystem;
        this.pidManager = new PIDManager("ArmPID", propManager, 0.01, 0, 0, 0.5, -.3);
        calibration = AttemptCalibrationState.NotCalibrated;
        autoCalibrationEnabled = propManager.createPersistentProperty("AutoCalibrationEnabled", false);
        autoCalibrationTimeout = propManager.createPersistentProperty("AutoCalibrationTimeout", 5.0);
        gaveUpCalibrating = propManager.createEphemeralProperty("GaveUpCalibrating", false);
        this.requires(this.armSubsystem);
        
        kickstandRaiseAngle = propManager.createPersistentProperty("KickstandRaiseAngle", 5.0);
        kickstandRaisePower = propManager.createPersistentProperty("KickstandRaisePower", 0.4);
        kickstandRaiseDuration = propManager.createPersistentProperty("KickstandRaiseDuration", 2.0);
    }

    @Override
    public void initialize() {
        pidManager.reset();
        
        // When this is initialized, make sure to set target angle to current
        // angle to avoid mechanical thrashing.
        armTargetSubsystem.setTargetAngle(armSubsystem.getArmAngle());
    }

    @Override
    public void execute() {
        
        boolean isCurrentlyCalibrated = armSubsystem.isCalibrated();
        
        if (!oldIsCalibrated && isCurrentlyCalibrated) {
            // we just got calibrated!
            armTargetSubsystem.setTargetAngle(0);
        }
        
        oldIsCalibrated = isCurrentlyCalibrated;
        
        // just in case the system underneath is not running        
        if (isCurrentlyCalibrated || gaveUpCalibrating.get()) {
        
            double currentArmAngle = armSubsystem.getArmAngle();
            double targetArmAngle = armTargetSubsystem.getTargetAngle();
    
            double armPower = pidManager.calculate(targetArmAngle, currentArmAngle);
    
            armSubsystem.setArmMotorPower(armPower);
        }
        else {
            attemptCalibration();
        }
         
    }

    private void attemptCalibration() {
        // System not calibrated! Let's try to fix that.
        
        if (!autoCalibrationEnabled.get()) {
            // TODO: once we have limit switches, enable automatic calibration
            armSubsystem.calibrateCurrentPositionAsLow();
        }
        else {
            switch (calibration) {
                case NotCalibrated:
                    beginCalibrationTime = Timer.getFPGATimestamp();
                    // Swap these lines if we have a kickstand and need to raise arm before lowering it
                    calibration = AttemptCalibrationState.WaitForArmToLower;
                    //calibration = AttemptCalibrationState.RaiseArmToLowerKickstand;
                    armSubsystem.setArmMotorToCalibratePower();
                    break;
                case RaiseArmToLowerKickstand:
                    double power = kickstandRaisePower.get();
                    
                    if (armSubsystem.getArmAngle() > kickstandRaiseAngle.get()) {
                        power = 0;
                    }
                    
                    armSubsystem.setArmMotorPower(power);
                    
                    if (Timer.getFPGATimestamp() - beginCalibrationTime > kickstandRaiseDuration.get()) {
                        calibration = AttemptCalibrationState.WaitForArmToLower;
                    }
                    
                    break;
                case WaitForArmToLower:
                    if (Timer.getFPGATimestamp() - beginCalibrationTime > autoCalibrationTimeout.get()) {
                        // calibration is taking too long - abort and only drive manually
                        calibration = AttemptCalibrationState.AbortCalibration;
                        giveUpCalibrating();
                        break;
                    }
                    armSubsystem.setArmMotorToCalibratePower();
                    break;
                case AbortCalibration:
                    // we could not calibrate - don't drive automatically.
                    giveUpCalibrating();
                    break;
                default: 
                    // no idea how you got here
                    giveUpCalibrating();
                    break;
            }
        }
    }
    
    private void giveUpCalibrating() {
        armSubsystem.setArmMotorPower(0);
        gaveUpCalibrating.set(true);
    }
    
    public boolean hasGivenUpCalibration() {
        return gaveUpCalibrating.get();
    }
}