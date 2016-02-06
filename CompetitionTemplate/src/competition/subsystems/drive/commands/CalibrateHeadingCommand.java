package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;


public class CalibrateHeadingCommand extends BaseCommand {

    PoseSubsystem pose;
    
    @Inject
    public CalibrateHeadingCommand(PoseSubsystem pose)
    {
        this.pose = pose;
    }
    
    @Override
    public void initialize() {
        pose.setCurrentHeading(pose.FACING_AWAY_FROM_DRIVERS);
    }

    @Override
    public void execute() {        
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
