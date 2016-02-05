package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;


public class UpdatePoseCommand extends BaseCommand {

    final PoseSubsystem pose;
    
    @Inject
    public UpdatePoseCommand(PoseSubsystem pose)
    {
        this.pose = pose;
        this.requires(pose);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        pose.updateCurrentHeading();
        
    }

}
