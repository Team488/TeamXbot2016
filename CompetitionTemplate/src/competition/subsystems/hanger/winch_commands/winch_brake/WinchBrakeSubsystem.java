package competition.subsystems.hanger.winch_commands.winch_brake;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class WinchBrakeSubsystem extends BaseSubsystem{
    public XSolenoid winchBrake;
    
    @Inject
    public WinchBrakeSubsystem(WPIFactory factory){
        winchBrake = factory.getSolenoid(1);
    }
    
    public void openBrake(){
        winchBrake.set(true);
    }
    
    public void closeBrake(){
        winchBrake.set(false);
    }
}
