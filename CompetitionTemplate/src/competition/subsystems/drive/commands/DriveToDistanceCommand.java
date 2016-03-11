package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class DriveToDistanceCommand extends HeadingDriveCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    
    double robotRelativeGoalDistance;
    
    DoubleProperty driveToDistanceThreshold;
    PIDManager travelManager;
    
    @Inject
    public DriveToDistanceCommand(
            DriveSubsystem drive, 
            PoseSubsystem pose, 
            XPropertyManager propMan, 
            HeadingModule heading) {
        super(drive, pose, propMan, heading);
        
        driveToDistanceThreshold = propMan.createPersistentProperty("DriveToDistanceThreshold", 2.0);
        travelManager = new PIDManager("DriveToDistance", propMan, 0.04, 0, 0, 0.5, -0.5);
    }
    
    public void setTargetDistance(double robotRelativeGoalDistance) {
        this.robotRelativeGoalDistance = robotRelativeGoalDistance;
    }
    

    @Override
    public void initialize() {
        pose.resetDistanceTraveled();        
    }

    @Override
    public void execute() {
        double goal = robotRelativeGoalDistance;
        double current = pose.getRobotOrientedTotalDistanceTraveled().y;
        
        double travelPower = travelManager.calculate(goal, current);
        double turningPower = calculateHeadingPower();
        
        double leftPower = travelPower - turningPower;
        double rightPower = travelPower + turningPower;
        
        drive.tankDriveSafely(leftPower, rightPower);        
    }
    
    @Override
    public boolean isFinished() {
        return travelManager.isOnTarget(driveToDistanceThreshold.get());
    }
    
    @Override
    public void end() {
        drive.stopDrive();
    }
}
