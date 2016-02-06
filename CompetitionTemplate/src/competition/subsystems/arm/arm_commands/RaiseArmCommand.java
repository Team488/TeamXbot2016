package competition.subsystems.arm.arm_commands;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class RaiseArmCommand extends BaseCommand {
    ArmSubsystem armSubsystem;

    public RaiseArmCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        armSubsystem.armMotorPower(1);
    }
    
    @Override
    public boolean isFinished() {
        return armSubsystem.isArmAtMaximumHeight();
    }
    
    @Override
    public void end() {
        armSubsystem.armMotorPower(0);
    }
        
}
