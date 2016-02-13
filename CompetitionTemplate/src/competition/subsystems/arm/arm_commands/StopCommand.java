package competition.subsystems.arm.arm_commands;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class StopCommand extends BaseCommand{
    ArmSubsystem armSubsystem;
    
    public StopCommand(ArmSubsystem armSubsystem){
        this.armSubsystem = armSubsystem;
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        armSubsystem.armMotorPower(0);
    }

}
