package competition.subsystems.autonomous;

import com.google.inject.Inject;
import com.google.inject.Provider;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;

import competition.subsystems.drive.commands.DriveToWallCommand;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import competition.subsystems.drive.commands.HeadingModule;

import edu.wpi.first.wpilibj.command.CommandGroup;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class LowBarScoreCommandGroup extends CommandGroup{
    public DoubleProperty distanceFromWallToTurningPoint;
    public DoubleProperty distanceToLowGoal;
    public DoubleProperty headingToFaceSideWall;
    public DoubleProperty distanceToTurningPointFromSideWall;
    public DoubleProperty headingToFaceLowGoal;
    DoubleProperty ballEjectTime;
    
    @Inject
    public LowBarScoreCommandGroup ( 
            XPropertyManager propManager,
            PoseSubsystem pose,
            DriveSubsystem drive,
            HeadingModule headingModule,
            CollectorSubsystem collector,
            Provider<DriveToWallCommand> driveToWallProvider,
            Provider<TurnToHeadingCommand> turnToHeadingProvider){
        DriveToWallCommand driveToWall = driveToWallProvider.get();
        distanceFromWallToTurningPoint = propManager.createPersistentProperty("distanceFromWallToTurningPointInInches", 90.3);
        driveToWall.setDesiredDistance(distanceFromWallToTurningPoint.get());
        
        this.addSequential(driveToWall);
        
        TurnToHeadingCommand turnToSideWall = turnToHeadingProvider.get();
        headingToFaceSideWall = propManager.createPersistentProperty("headingToFaceSideWall", 270.0);
        turnToSideWall.setTargetHeading(headingToFaceSideWall.get());
        
        this.addSequential(turnToSideWall);
        
        DriveToWallCommand driveToTurningPoint = driveToWallProvider.get();
        distanceToTurningPointFromSideWall = propManager.createPersistentProperty("turningPointDistanceFromSideWall", 32.0);
        driveToTurningPoint.setDesiredDistance(distanceToTurningPointFromSideWall.get());
        
        this.addSequential(driveToTurningPoint);
        
        headingToFaceLowGoal = propManager.createPersistentProperty("Target heading to turn to low Goal", 150.0);
        TurnToHeadingCommand turnToLowGoal = turnToHeadingProvider.get();
        turnToLowGoal.setTargetHeading(headingToFaceLowGoal.get());
        
        this.addSequential(turnToLowGoal);
        
        distanceToLowGoal = propManager.createPersistentProperty("distance to low goal from turning point", 139.03);
        DriveToDistanceCommand driveToLowGoal = new DriveToDistanceCommand(drive, pose, propManager, headingModule);
        driveToLowGoal.setTargetDistance(distanceToLowGoal.get());
        
        this.addSequential(driveToLowGoal);
        
        ballEjectTime = propManager.createPersistentProperty("timeout for ejecting ball", 2.0);
        CollectorEjectCommand collectorEjectCommand = new CollectorEjectCommand(collector, propManager);
        
        this.addSequential(collectorEjectCommand, ballEjectTime.get());
        
    }

}
