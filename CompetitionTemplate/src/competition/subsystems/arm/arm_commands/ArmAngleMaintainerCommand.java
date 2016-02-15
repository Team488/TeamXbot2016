package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;

@Singleton
public class ArmAngleMaintainerCommand extends BaseCommand{
    public ArmSubsystem armSubsystem;
    public ArmTargetSubsystem armTargetSubsystem;
    double armPower;
    
    @Inject    
    public ArmAngleMaintainerCommand(ArmSubsystem armSubsystem, ArmTargetSubsystem armTargetSubsystem){
        this.armSubsystem = armSubsystem;
        this.armTargetSubsystem = armTargetSubsystem;
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        double currentArmAngle = armSubsystem.getArmAngle();
        double targetArmAngle = armTargetSubsystem.getTargetAngle();
        
        armPower = (targetArmAngle - currentArmAngle) / 90;
        armSubsystem.setArmMotorPower(armPower);
    }
}