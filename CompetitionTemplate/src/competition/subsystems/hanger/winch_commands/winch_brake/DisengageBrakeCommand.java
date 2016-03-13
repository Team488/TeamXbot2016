package competition.subsystems.hanger.winch_commands.winch_brake;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;

public class DisengageBrakeCommand extends BaseCommand{
    WinchBrakeSubsystem winchBrakeSubsystem;

    @Inject
    public DisengageBrakeCommand(WinchBrakeSubsystem winchBrakeSubsystem){
        this.winchBrakeSubsystem = winchBrakeSubsystem;
        this.requires(this.winchBrakeSubsystem);
    }
    
    @Override
    public void initialize() {
        winchBrakeSubsystem.openBrake();
    }

    @Override
    public void execute() {
        
    }

}

