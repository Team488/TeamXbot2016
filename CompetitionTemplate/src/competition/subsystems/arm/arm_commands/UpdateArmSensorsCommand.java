package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class UpdateArmSensorsCommand extends BaseCommand {

    ArmSubsystem armSubsystem;
    
    @Inject
    public UpdateArmSensorsCommand(ArmSubsystem armSubsystem) {
        this.armSubsystem = armSubsystem;
        this.setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
       armSubsystem.updateSensors();
    }

    
}
