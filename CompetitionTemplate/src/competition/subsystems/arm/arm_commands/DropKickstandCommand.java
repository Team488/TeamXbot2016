package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import edu.wpi.first.wpilibj.Timer;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class DropKickstandCommand extends BaseCommand {

    ArmSubsystem arm;
    
    DoubleProperty kickstandRaiseAngle;
    DoubleProperty kickstandRaisePower;
    DoubleProperty kickstandRaiseDuration;
    
    double startTime;
    
    @Inject
    public DropKickstandCommand(ArmSubsystem arm, XPropertyManager propMan) {
        kickstandRaiseAngle = propMan.createPersistentProperty("KickstandRaiseAngle", 5.0);
        kickstandRaisePower = propMan.createPersistentProperty("KickstandRaisePower", 0.2);
        kickstandRaiseDuration = propMan.createPersistentProperty("KickstandRaiseDuration", 2.0);
        
        this.arm = arm;
        this.requires(arm);
    }

    @Override
    public void initialize() {
        startTime = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        double power = kickstandRaisePower.get();
        
        if (!arm.isCalibrated())
        {
            // if the arm is uncalibrated, this is probably happening at the start of the match.
            if (arm.getArmAngle() > kickstandRaiseAngle.get()) {
                power = 0;
            }
        }
        
        arm.setArmMotorPower(power);
    }
    
    @Override
    public boolean isFinished() {
        return Timer.getFPGATimestamp() - startTime > kickstandRaiseDuration.get();
    }
    
    @Override
    public void end() {
        arm.setArmMotorPower(0);
    }
}
