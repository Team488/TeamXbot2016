package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;

public class ArmToTopCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    ArmTargetSubsystem armTargetSubsystem;

    @Inject
    public ArmToTopCommand(ArmSubsystem armSubsystem, ArmTargetSubsystem armTargetSubsystem) {
        this.armSubsystem = armSubsystem;
        this.armTargetSubsystem = armTargetSubsystem;
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        armTargetSubsystem.setTargetAngle(90);
    }

    @Override
    public void execute() {
        
    }
    
    @Override
    public boolean isFinished() {
        return armSubsystem.getArmAngle() >= armTargetSubsystem.getTargetAngle();
    }
    
    @Override
    public void end() {
        armSubsystem.armMotorPower(0);
    }
        
}
