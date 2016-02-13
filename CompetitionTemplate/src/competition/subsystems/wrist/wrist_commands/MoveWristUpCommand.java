package competition.subsystems.wrist.wrist_commands;

import competition.subsystems.wrist.WristSubsystem;
import xbot.common.command.BaseCommand;

public class MoveWristUpCommand extends BaseCommand{
    WristSubsystem wristSubsystem;
    
    public MoveWristUpCommand (WristSubsystem wristSubsystem) {
        this.wristSubsystem = wristSubsystem;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void initialize() {
        wristSubsystem.moveWristUp();
    }
    

}
