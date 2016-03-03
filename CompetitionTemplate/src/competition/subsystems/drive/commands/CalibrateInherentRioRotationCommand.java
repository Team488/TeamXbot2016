package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;


public class CalibrateInherentRioRotationCommand extends BaseCommand {

    PoseSubsystem pose;
    
    @Inject
    public CalibrateInherentRioRotationCommand(PoseSubsystem pose)
    {
        this.pose = pose;
        this.setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {
        pose.calibrateInherentRioOrientation();
    }

    @Override
    public void execute() {        
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
