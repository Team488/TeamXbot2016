package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class DriveToDistanceCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    HeadingModule heading;
    
    double robotRelativeGoalDistance;
    
    DoubleProperty driveToDistanceThreshold;
    PIDManager travelManager;
    
    double startingHeading;
    
    @Inject
    public DriveToDistanceCommand(
            DriveSubsystem drive, 
            PoseSubsystem pose, 
            XPropertyManager propMan, 
            HeadingModule heading) {
        
        driveToDistanceThreshold = propMan.createPersistentProperty("DriveToDistanceThreshold", 2.0);
        travelManager = new PIDManager("DriveToDistance", propMan, 0.04, 0, 0, 0.5, -0.5);
        
        this.drive = drive;
        this.heading = heading;
        this.pose = pose;
    }
    
    public void setTargetDistance(double robotRelativeGoalDistance) {
        this.robotRelativeGoalDistance = robotRelativeGoalDistance;
    }    

    @Override
    public void initialize() {
        pose.resetDistanceTraveled();        
        startingHeading = pose.getCurrentHeading().getValue();
    }

    @Override
    public void execute() {
        double goal = robotRelativeGoalDistance;
        double current = pose.getRobotOrientedTotalDistanceTraveled().y;
        
        double turningPower = heading.calculateHeadingPower(startingHeading);
        double travelPower = travelManager.calculate(goal, current);
        
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
