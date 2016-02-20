package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sun.org.apache.xerces.internal.impl.PropertyManager;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import edu.wpi.first.wpilibj.Timer;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class ArmAngleMaintainerCommand extends BaseCommand{
    public ArmSubsystem armSubsystem;
    public ArmTargetSubsystem armTargetSubsystem;
    double armPower;
    AttemptCalibrationState calibration;
    BooleanProperty autoCalibrationEnabled;
    double beginCalibrationTime = 0;
    
    PIDManager pidManager;
    
    public enum AttemptCalibrationState {
        NotCalibrated,
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
        this.pidManager = new PIDManager("ArmPID", propManager, 0.01, 0, 0);
        calibration = AttemptCalibrationState.NotCalibrated;
        autoCalibrationEnabled = propManager.createPersistentProperty("AutoCalibrationEnabled", false);
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        pidManager.reset();
    }

    @Override
    public void execute() {
        
        // just in case the system underneath is not running
        armSubsystem.updateSensors();
        
        if (armSubsystem.isCalibrated()) {
        
            double currentArmAngle = armSubsystem.getArmAngle();
            double targetArmAngle = armTargetSubsystem.getTargetAngle();
    
            double armPower = pidManager.calculate(targetArmAngle, currentArmAngle);
    
            armSubsystem.setArmMotorPower(armPower);
        }
        else {
            // System not calibrated! Let's try to fix that.
            
            if (!autoCalibrationEnabled.get()) {
                // TODO: once we have limit switches, enable automatic calibration
                armSubsystem.forceCalibrateLow();
            }
            else {
                switch (calibration) {
                    case NotCalibrated:
                        beginCalibrationTime = Timer.getFPGATimestamp();
                        calibration = AttemptCalibrationState.WaitForArmToLower;
                        armSubsystem.calibrateArm();
                        break;
                    case WaitForArmToLower:
                        if (Timer.getFPGATimestamp() - beginCalibrationTime > 5.0) {
                            // calibration is taking too long - abort and only drive manually
                            calibration = AttemptCalibrationState.AbortCalibration;
                            armSubsystem.setArmMotorPower(0);
                            break;
                        }
                        armSubsystem.calibrateArm();
                        break;
                    case AbortCalibration:
                        // we could not calibrate - don't drive automatically.
                        armSubsystem.setArmMotorPower(0);
                        break;
                    default: 
                        // no idea how you got here
                        armSubsystem.setArmMotorPower(0);
                        break;
                }
            }
        }
    }
}