package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class WinchExtendCommand extends BaseCommand{
    WinchSubsystem winchSubsystem;
    DoubleProperty winchExtentionPower;
    
    @Inject
    public WinchExtendCommand (WinchSubsystem winchSubsystem, XPropertyManager propManager){
        this.winchSubsystem = winchSubsystem;
        winchExtentionPower = propManager.createPersistentProperty("winch extention power", -1.0);
        this.requires(winchSubsystem);
    }

    @Override
    public void initialize() {
        winchSubsystem.setWinchMotorPower(winchExtentionPower.get());
    }

    @Override
    public void execute() {
        
    }
    
    public void end(){
        winchSubsystem.setWinchMotorPower(0);
    }

}
