package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;

public class EngageBreakCommand extends BaseCommand{
    WinchSubsystem winchSubsystem;
    
    @Inject
    public EngageBreakCommand(WinchSubsystem winchSubsystem){
        this.winchSubsystem = winchSubsystem;
    }

    @Override
    public void initialize() {
        winchSubsystem.closeBreak();
    }

    @Override
    public void execute() {
        
    }
}
