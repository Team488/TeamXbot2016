package competition.subsystems.hanger.winch_commands.winch_brake;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;

@Singleton
public class WinchBrakeSubsystem extends BaseSubsystem{
    public XSolenoid winchBrake;
    
    @Inject
    public WinchBrakeSubsystem(){
    }
    
    public void openBrake(){
        winchBrake.set(true);
    }
    
    public void closeBrake(){
        winchBrake.set(false);
    }
}
