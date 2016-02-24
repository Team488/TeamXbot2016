package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class WinchRetractCommand extends BaseCommand{
    WinchSubsystem winchSubsystem;
    DoubleProperty winchRetractionPower;
    
    @Inject
    public WinchRetractCommand (WinchSubsystem winchSubsystem, XPropertyManager propManager){
        this.winchSubsystem = winchSubsystem;
        winchRetractionPower = propManager.createPersistentProperty("winch retraction power", 1.0);
        this.requires(winchSubsystem);
    }

    @Override
    public void initialize() {
        winchSubsystem.setWinchMotorPower(winchRetractionPower.get());
    }

    @Override
    public void execute() {
        
    }
    
    public void end(){
        winchSubsystem.setWinchMotorPower(0);
    }

}
