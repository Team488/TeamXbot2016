package competition.subsystems.arm.arm_commands;
import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class LowerArmCommand extends BaseCommand {
    ArmSubsystem armSubsystem;

    public LowerArmCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        armSubsystem.armMotorPower(-1);
    }
    
    @Override
    public boolean isFinished() {
        return armSubsystem.isArmAtMinimumHeight();
    }
    
    @Override
    public void end() {
        armSubsystem.armMotorPower(0);
    }
        
}