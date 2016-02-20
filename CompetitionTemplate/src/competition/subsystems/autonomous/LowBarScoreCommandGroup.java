package competition.subsystems.autonomous;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.HeadingModule;
import edu.wpi.first.wpilibj.command.CommandGroup;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class LowBarScoreCommandGroup extends CommandGroup{
    public DoubleProperty distanceToTurningPoint;
    public DoubleProperty distanceToLowGoal;
    DoubleProperty targetHeading;
    
    @Inject
    public LowBarScoreCommandGroup (DriveSubsystem driveSubsystem, HeadingModule headingModule, PoseSubsystem poseSubsystem, XPropertyManager propManager){
        distanceToTurningPoint = propManager.createPersistentProperty("distance to turning point", 99.7);
        DriveForDistanceCommand driveToTurningPoint = new DriveForDistanceCommand(poseSubsystem, driveSubsystem, propManager);
        driveToTurningPoint.setTargetDistance(distanceToTurningPoint.get());
        
        this.addSequential(driveToTurningPoint);
        
        targetHeading = propManager.createPersistentProperty("Target heading to turn to lowGoal", 60.0);
        TurnToHeadingCommand turnToLowGoal = new TurnToHeadingCommand(driveSubsystem, headingModule, poseSubsystem, propManager);
        turnToLowGoal.setTargetHeading(targetHeading.get());
        
        this.addSequential(turnToLowGoal);
        
        distanceToLowGoal = propManager.createPersistentProperty("distance to low goal", 139.03);
        DriveForDistanceCommand driveToLowGoal = new DriveForDistanceCommand(poseSubsystem, driveSubsystem, propManager);
        driveToLowGoal.setTargetDistance(distanceToLowGoal.get());
        
        this.addSequential(driveToLowGoal);
        
    }

}
