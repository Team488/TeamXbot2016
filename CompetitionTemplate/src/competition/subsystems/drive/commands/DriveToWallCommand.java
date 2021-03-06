package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class DriveToWallCommand extends BaseCommand{
    PoseSubsystem pose;
    DriveSubsystem drive;
    PIDManager wallManager;
    double targetDistanceFromWall;
    DoubleProperty driveToWallErrorTolerance;
    
    @Inject
    public DriveToWallCommand(PoseSubsystem pose, XPropertyManager propMan, DriveSubsystem drive)
    {
        this.drive = drive;
        this.pose = pose;
        driveToWallErrorTolerance = propMan.createPersistentProperty("driveToWallErrorTolerance", 2.0);
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
    
    public boolean isFinished(){
        return Math.abs(targetDistanceFromWall - pose.getFrontRangefinderDistance()) < driveToWallErrorTolerance.get(); 
    }
    
    @Override
    public void end() {
        drive.tankDriveSafely(0,0);
        
    }
}
