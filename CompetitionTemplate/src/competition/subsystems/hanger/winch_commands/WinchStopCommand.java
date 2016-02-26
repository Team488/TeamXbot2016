package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;

public class WinchStopCommand extends BaseCommand{
    WinchSubsystem winchSubsystem;
    
    @Inject
    public WinchStopCommand(WinchSubsystem winchSubsystem){
        this.winchSubsystem =  winchSubsystem;
        this.requires(winchSubsystem);
    }

    @Override
    public void initialize() {
        winchSubsystem.setWinchMotorPower(0);
    }

    @Override
    public void execute() {
        
    }

}
