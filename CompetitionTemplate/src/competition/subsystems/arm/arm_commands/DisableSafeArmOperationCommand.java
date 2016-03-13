package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class DisableSafeArmOperationCommand extends BaseCommand{
    ArmSubsystem armSubsystem;
    
    @Inject
    public DisableSafeArmOperationCommand(ArmSubsystem armSubsystem){
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {
        armSubsystem.disableSafeArmOperation();
    }

    @Override
    public void execute() {
        
    }
    
    public void end(){
        armSubsystem.enableSafeArmOperation();
    }
}
