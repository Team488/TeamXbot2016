package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class CalibrateArmLowCommand extends BaseCommand {

    ArmSubsystem arm;
    
    @Inject
    public CalibrateArmLowCommand(ArmSubsystem arm) {
        this.arm = arm;
    }
    
    @Override
    public void initialize() {
        arm.calibrateCurrentPositionAsLow();
    }

    @Override
    public void execute() {
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
