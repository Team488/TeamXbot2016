package competition.subsystems.arm.arm_commands;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class RaiseArm extends BaseCommand {
    ArmSubsystem armSubsystem;

    public RaiseArm(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if (armSubsystem.isArmAtMaximumHeight()){
            end();
        }else {
            armSubsystem.armMotor.set(1);
        }
    }
    
    @Override
    public void end() {
        armSubsystem.armMotor.set(0);
    }
        
}
