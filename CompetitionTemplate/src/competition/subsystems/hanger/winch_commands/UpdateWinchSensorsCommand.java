package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.hanger.HookSubsystem;
import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;

public class UpdateWinchSensorsCommand extends BaseCommand {
    WinchSubsystem winchSubsystem;
    
    @Inject
    public UpdateWinchSensorsCommand(WinchSubsystem winchSubsystem) {
        this.winchSubsystem = winchSubsystem;
        this.setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        winchSubsystem.getWinchDistance();
    }

}
