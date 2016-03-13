package competition.subsystems.hanger.winch_commands.winch_brake;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;

public class EngageBrakeCommand extends BaseCommand{
    WinchBrakeSubsystem winchBrakeSubsystem;
    
    @Inject
    public EngageBrakeCommand(WinchBrakeSubsystem winchBrakeSubsystem){
        this.winchBrakeSubsystem = winchBrakeSubsystem;
        this.requires(this.winchBrakeSubsystem);
    }

    @Override
    public void initialize() {
        winchBrakeSubsystem.closeBrake();
    }

    @Override
    public void execute() {
        
    }
}
