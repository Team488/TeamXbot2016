package competition.subsystems.wrist.wrist_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.wrist.WristSubsystem;
import xbot.common.command.BaseCommand;

public class MoveWristUpCommand extends BaseCommand {
    WristSubsystem wristSubsystem;
    ArmSubsystem armSubsystem;
    
    @Inject
    public MoveWristUpCommand (WristSubsystem wristSubsystem, ArmSubsystem armSubsystem) {
        this.wristSubsystem = wristSubsystem;
        this.armSubsystem = armSubsystem;
        this.requires(this.wristSubsystem);
    }

    @Override
    public void execute() {
        if (armSubsystem.isArmInDangerZone()) {
            // if we try to extend the wrist while the arm is fairly low, it will break the 15" perimeter extension rule.
            // When we get in the danger zone (or the illegal zone, which is a subset of danger zone), force the wrist down.
            wristSubsystem.moveWristDown();
        }
        else {
            // we're good to go!
            wristSubsystem.moveWristUp();
        }
    }

    @Override
    public void initialize() {
        
    }
    
    public boolean isFinished() {
        return true;
    }

}
