package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.XPropertyManager;

@Singleton
public class ArmAngleMaintainerCommand extends BaseCommand{
    public ArmSubsystem armSubsystem;
    public ArmTargetSubsystem armTargetSubsystem;
    double armPower;
    
    PIDManager pidManager;
    
    @Inject
    public ArmAngleMaintainerCommand(ArmSubsystem armSubsystem, ArmTargetSubsystem armTargetSubsystem,
            XPropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        this.armTargetSubsystem = armTargetSubsystem;
        this.pidManager = new PIDManager("ArmPID", propManager, 0.1, 0, 0);
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double currentArmAngle = armSubsystem.getArmAngle();
        double targetArmAngle = armTargetSubsystem.getTargetAngle();

        double armPower = pidManager.calculate(targetArmAngle, currentArmAngle);

        armSubsystem.setArmMotorPower(armPower);
    }
}