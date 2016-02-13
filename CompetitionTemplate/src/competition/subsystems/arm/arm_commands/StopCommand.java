package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class StopCommand extends BaseCommand{
    ArmSubsystem armSubsystem;
    
    @Inject
    public StopCommand(ArmSubsystem armSubsystem){
        this.armSubsystem = armSubsystem;
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        armSubsystem.armMotorPower(0);
    }

    @Override
    public void execute() {
        
    }

}
