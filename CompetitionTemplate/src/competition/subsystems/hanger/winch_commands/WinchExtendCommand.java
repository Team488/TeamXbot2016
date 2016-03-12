package competition.subsystems.hanger.winch_commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class WinchExtendCommand extends BaseCommand{
    private static Logger log = Logger.getLogger(WinchExtendCommand.class);
    WinchSubsystem winchSubsystem;
    public DoubleProperty winchExtentionPower;
    
    @Inject
    public WinchExtendCommand (WinchSubsystem winchSubsystem, XPropertyManager propManager){
        winchExtentionPower = propManager.createPersistentProperty("winch extention power", 1.0);
        this.winchSubsystem = winchSubsystem;
        this.requires(winchSubsystem);
    }

    @Override
    public void initialize() {
        log.info("initializing WinchExtendCommand");
        winchSubsystem.setWinchMotorPower(winchExtentionPower.get());
    }

    @Override
    public void execute() {
        winchSubsystem.setWinchMotorPower(winchExtentionPower.get());
    }
    
    public void end(){
        winchSubsystem.setWinchMotorPower(0);
    }

}
