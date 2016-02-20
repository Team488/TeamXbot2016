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
    
    PIDManager pidManager;
    
    public enum AttemptCalibrationState {
        NotCalibrated,
        WaitForArmToLower,
        AbortCalibration
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
    
    private double beginCalibrationTime = 0;

    @Override
    public void execute() {
        if (armSubsystem.isCalibrated()) {
        
            double currentArmAngle = armSubsystem.getArmAngle();
            double targetArmAngle = armTargetSubsystem.getTargetAngle();
    
            double armPower = pidManager.calculate(targetArmAngle, currentArmAngle);
    
            armSubsystem.setArmMotorPower(armPower);
        }
        else {
            // System uncalibrated!
            
            if (!autoCalibrationEnabled.get()) {
                // TODO: once we have limit switches, enable automatic calibration
                armSubsystem.forceCalibrateLow();
            }
            else {
                switch (calibration) {
                case NotCalibrated:
                    beginCalibrationTime = Timer.getFPGATimestamp();
                    calibration = AttemptCalibrationState.WaitForArmToLower;
                    break;
                case WaitForArmToLower:
                    if (Timer.getFPGATimestamp() - beginCalibrationTime > 5.0) {
                        // calibration is taking too long - abort and only drive manually
                        calibration = AttemptCalibrationState.AbortCalibration;
                    }
                    armSubsystem.calibrateArm();
                    break;
                case AbortCalibration:
                    // we could not calibrate - don't drive automatically.
                    armSubsystem.setArmMotorPower(0);
                    break;
                }
            }
        }
    }
}