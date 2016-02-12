package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.PoseSubsystem.DefenseState;
import xbot.common.command.BaseCommand;
import xbot.common.math.ContiguousDouble;


public class UpdatePoseCommand extends BaseCommand {

    final PoseSubsystem pose;
    final MonitorDefenseTraversalModule defenseTraversal;
    
    @Inject
    public UpdatePoseCommand(PoseSubsystem pose, MonitorDefenseTraversalModule defenseTraversal)
    {
        this.pose = pose;
        this.defenseTraversal = defenseTraversal;
        this.requires(pose);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        ContiguousDouble heading = pose.getCurrentHeading();
        defenseTraversal.measureState(Math.abs(heading.getValue()));        
    }

}
