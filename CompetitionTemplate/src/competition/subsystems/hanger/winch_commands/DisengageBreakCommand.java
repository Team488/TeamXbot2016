package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;

public class DisengageBreakCommand extends BaseCommand{
    WinchSubsystem winchSubsystem;

    @Inject
    public DisengageBreakCommand(WinchSubsystem winchSubsystem){
        this.winchSubsystem = winchSubsystem;
    }
    
    @Override
    public void initialize() {
        winchSubsystem.openBreak();
    }

    @Override
    public void execute() {
        
    }

}

