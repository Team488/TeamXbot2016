package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;

public class ResetRobotPositionCommand extends BaseCommand {

    PoseSubsystem pose;
    
    @Inject
    public ResetRobotPositionCommand(PoseSubsystem pose) {
        this.pose = pose;
    }
    
    @Override
    public void initialize() {
        pose.resetDistanceTraveled();
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
