package competition.subsystems.wrist.wrist_commands;

import com.google.inject.Inject;

import competition.subsystems.wrist.WristSubsystem;
import xbot.common.command.BaseCommand;

public class MoveWristUpCommand extends BaseCommand {
    WristSubsystem wristSubsystem;
    
    @Inject
    public MoveWristUpCommand (WristSubsystem wristSubsystem) {
        this.wristSubsystem = wristSubsystem;
        this.requires(this.wristSubsystem);
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void initialize() {
        wristSubsystem.moveWristUp();
    }
    
    public boolean isFinished() {
        return true;
    }

}
