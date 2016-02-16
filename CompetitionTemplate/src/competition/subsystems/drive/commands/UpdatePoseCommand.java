    package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.ContiguousDouble;


public class UpdatePoseCommand extends BaseCommand {

    final PoseSubsystem pose;
    final MonitorDefenseTraversalModule defenseTraversal;
    
    @Inject
    public UpdatePoseCommand(PoseSubsystem pose, MonitorDefenseTraversalModule defenseTraversal)
    {
        this.setRunWhenDisabled(true);
        this.pose = pose;
        this.defenseTraversal = defenseTraversal;
        this.requires(pose);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        pose.updateCurrentHeading();
        defenseTraversal.measureState(Math.abs(pose.getRobotPitch()));
    }

}
