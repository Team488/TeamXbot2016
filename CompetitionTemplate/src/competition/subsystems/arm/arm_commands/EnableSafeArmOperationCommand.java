package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class EnableSafeArmOperationCommand extends BaseCommand{
    ArmSubsystem armSubsystem;
    
    @Inject
    public EnableSafeArmOperationCommand(ArmSubsystem armSubsystem){
        this.armSubsystem = armSubsystem;
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        armSubsystem.enableSafeArmOperation();
    }

    @Override
    public void execute() {
        
    }

}
