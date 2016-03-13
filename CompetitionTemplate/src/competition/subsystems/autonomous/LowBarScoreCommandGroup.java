package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import competition.subsystems.drive.commands.HeadingModule;
import edu.wpi.first.wpilibj.command.CommandGroup;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class LowBarScoreCommandGroup extends CommandGroup{
    public DoubleProperty distanceToTurningPoint;
    public DoubleProperty distanceToLowGoal;
    public DoubleProperty targetHeading;
    DoubleProperty ballEjectTime;
    
    @Inject
    public LowBarScoreCommandGroup (
            DriveSubsystem drive, 
            HeadingModule headingModule, 
            PoseSubsystem pose, 
            CollectorSubsystem collector, 
            XPropertyManager propManager){
        distanceToTurningPoint = propManager.createPersistentProperty("distance to turning point from far end of low goal", -147.7);
        DriveToDistanceCommand driveToTurningPoint = new DriveToDistanceCommand(drive, pose, propManager, headingModule);
        driveToTurningPoint.setTargetDistance(distanceToTurningPoint.get());
        
        this.addSequential(driveToTurningPoint);
        
        targetHeading = propManager.createPersistentProperty("Target heading to turn to low Goal", 240.0);
        TurnToHeadingCommand turnToLowGoal = new TurnToHeadingCommand(drive, headingModule, pose, propManager);
        turnToLowGoal.setTargetHeading(targetHeading.get());
        
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
