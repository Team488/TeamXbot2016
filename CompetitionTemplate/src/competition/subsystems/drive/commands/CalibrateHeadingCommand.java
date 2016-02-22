package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;


public class CalibrateHeadingCommand extends BaseCommand {

    PoseSubsystem pose;
    double heading;
    
    @Inject
    public CalibrateHeadingCommand(PoseSubsystem pose)
    {
        this.pose = pose;
        heading = pose.FACING_AWAY_FROM_DRIVERS;
    }
    
    public void setHeading(double heading) {
        this.heading = heading;
    }
    
    @Override
    public void initialize() {
        pose.setCurrentHeading(heading);
    }

    @Override
    public void execute() {        
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
