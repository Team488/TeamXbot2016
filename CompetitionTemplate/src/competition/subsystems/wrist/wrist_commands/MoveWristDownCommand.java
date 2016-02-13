package competition.subsystems.wrist.wrist_commands;

import competition.subsystems.wrist.WristSubsystem;
import xbot.common.command.BaseCommand;

public class MoveWristDownCommand extends BaseCommand{
    WristSubsystem wristSubsystem;
    
    public MoveWristDownCommand (WristSubsystem wristSubsystem) {
        this.wristSubsystem = wristSubsystem;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void initialize() {
        wristSubsystem.moveWristDown();
    }
    

}
