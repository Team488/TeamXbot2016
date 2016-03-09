package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class WaitForArmCalibrationCommand extends BaseCommand{

    ArmSubsystem arm;
    
    @Inject
    public WaitForArmCalibrationCommand(ArmSubsystem arm) {
        this.arm = arm;
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
    }
    
    @Override
    public boolean isFinished() {
        return arm.isCalibrated();
    }

}
