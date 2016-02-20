package competition.subsystems.low_bar_to_low_goal;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.autonomous.DriveForDistanceCommand;
import competition.subsystems.autonomous.LowBarScoreCommandGroup;
import competition.subsystems.autonomous.TurnToHeadingCommand;
import competition.subsystems.drive.DriveTestBase;
import competition.subsystems.drive.PoseSubsystem;
import edu.wpi.first.wpilibj.MockEncoder;

public class LowBarLowScoreCommandGroupTest extends DriveTestBase{
    LowBarScoreCommandGroup lowScoreCommands;
    
    @Before
    public void setup() {
        super.setup();
        this.lowScoreCommands = this.injector.getInstance(LowBarScoreCommandGroup.class);
    }
    
    @Test
    public void testDriveToTurningPoint() {
        DriveForDistanceCommand driveForDistanceCommand = this.injector.getInstance(DriveForDistanceCommand.class);
        
        PoseSubsystem poseSubsystem = this.injector.getInstance(PoseSubsystem.class);
        
        driveForDistanceCommand.setTargetDistance(lowScoreCommands.distanceToTurningPoint.get());
        
        ((MockEncoder)poseSubsystem.leftDriveEncoder).setDistance(0);
        
        ((MockEncoder)poseSubsystem.rightDriveEncoder).setDistance(0);
        
        driveForDistanceCommand.initialize();
        
        driveForDistanceCommand.execute();
       
        checkChassisPower(-1, -1);
        
        ((MockEncoder)poseSubsystem.leftDriveEncoder).setDistance(lowScoreCommands.distanceToTurningPoint.get());
        
        ((MockEncoder)poseSubsystem.rightDriveEncoder).setDistance(lowScoreCommands.distanceToTurningPoint.get());
        
        driveForDistanceCommand.execute();
        
        driveForDistanceCommand.isFinished();
        
        driveForDistanceCommand.end();
        
        checkChassisPower(0, 0);
        
    }
    
    @Test
    public void testTurnToHeadingCommand() {
        TurnToHeadingCommand turnToHeadingCommand = this.injector.getInstance(TurnToHeadingCommand.class);
        
        turnToHeadingCommand.setTargetHeading(lowScoreCommands.distanceToTurningPoint.get());
        
        setMockGyroHeading(0);
        
        turnToHeadingCommand.execute();
        
        verifyTurningRight();
        
        setMockGyroHeading(lowScoreCommands.distanceToTurningPoint.get());
        
        turnToHeadingCommand.execute();
        
        turnToHeadingCommand.isFinished();
        
        turnToHeadingCommand.end();
        
        checkChassisPower(0, 0);
    }
    
    @Test
    public void testDriveToLowGoal() {
        DriveForDistanceCommand driveForDistanceCommand = this.injector.getInstance(DriveForDistanceCommand.class);
        
        PoseSubsystem poseSubsystem = this.injector.getInstance(PoseSubsystem.class);
        
        driveForDistanceCommand.setTargetDistance(lowScoreCommands.distanceToLowGoal.get());
        
        ((MockEncoder)poseSubsystem.leftDriveEncoder).setDistance(0);
        
        driveForDistanceCommand.initialize();
        
        driveForDistanceCommand.execute();
       
        checkChassisPower(1, 1);
        
        ((MockEncoder)poseSubsystem.leftDriveEncoder).setDistance(lowScoreCommands.distanceToLowGoal.get());
        
        ((MockEncoder)poseSubsystem.rightDriveEncoder).setDistance(lowScoreCommands.distanceToLowGoal.get());
        
        driveForDistanceCommand.execute();
        
        driveForDistanceCommand.isFinished();
        
        driveForDistanceCommand.end();
        
        checkChassisPower(0, 0);
        
    }

}
