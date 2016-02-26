package competition.subsystems.hanger.winch_commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;

public class WinchStopCommand extends BaseCommand{
    private static Logger log = Logger.getLogger(WinchStopCommand.class);
    WinchSubsystem winchSubsystem;
    
    @Inject
    public WinchStopCommand(WinchSubsystem winchSubsystem){
        this.winchSubsystem =  winchSubsystem;
        this.requires(winchSubsystem);
    }

    @Override
    public void initialize() {
        log.info("initializing WinchStopCommand");
        winchSubsystem.setWinchMotorPower(0);
    }

    @Override
    public void execute() {
        
    }

}
