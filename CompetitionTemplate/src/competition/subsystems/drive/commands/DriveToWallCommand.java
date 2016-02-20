package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.XPropertyManager;

public class DriveToWallCommand extends BaseCommand 
{
    PoseSubsystem pose;
    DriveSubsystem drive;
    PIDManager wallManager;
    double targetDistanceFromWall;
    
    @Inject
    public DriveToWallCommand(PoseSubsystem pose, XPropertyManager propMan, DriveSubsystem drive)
    {
        this.drive = drive;
        this.pose = pose;
        wallManager = new PIDManager("FrontWallManager", propMan);
        
        this.requires(drive);
    }
    
    @Override
    public void initialize() {
        wallManager.reset();
    }
    
    public void setDesiredDistance(double distance) {
        targetDistanceFromWall = distance;
    }

    @Override
    public void execute() {
        // approach our desired distance
        // negate the power, since if we are at 100 distance and goal is 40, we will get back a negative result,
        // but we need a positive power to move forward.
        double power = -wallManager.calculate(targetDistanceFromWall, pose.getFrontRangefinderDistance());
        
        drive.tankDriveSafely(power, power);
    }
    
    @Override
    public void end() {
        drive.tankDriveSafely(0,0);
    }
}
