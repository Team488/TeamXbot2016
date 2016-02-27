package competition.subsystems.hanger.winch_commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class WinchRetractCommand extends BaseCommand{
    private static Logger log = Logger.getLogger(WinchExtendCommand.class);
    WinchSubsystem winchSubsystem;
    public DoubleProperty winchRetractionPower;
    
    @Inject
    public WinchRetractCommand (WinchSubsystem winchSubsystem, XPropertyManager propManager){
        this.winchSubsystem = winchSubsystem;
        winchRetractionPower = propManager.createPersistentProperty("winch retraction power", 1.0);
        this.requires(winchSubsystem);
    }

    @Override
    public void initialize() {
        log.info("initializing WinchRetractCommand");
        winchSubsystem.setWinchMotorPower(winchRetractionPower.get());
    }

    @Override
    public void execute() {
        
    }
    
    public void end(){
        winchSubsystem.setWinchMotorPower(0);
    }

}
