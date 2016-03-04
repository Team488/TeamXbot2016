package competition.subsystems.arm.arm_commands;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class EnableSafeArmOperationCommand extends BaseCommand{
    ArmSubsystem armSubsystem;
    
    public EnableSafeArmOperationCommand(ArmSubsystem armSubsystem){
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {
        armSubsystem.enableSafeArmOperation();
    }

    @Override
    public void execute() {
        
    }

}
