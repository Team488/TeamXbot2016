package competition.subsystems.low_bar_to_low_goal;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.autonomous.LowBarScoreCommandGroup;
import competition.subsystems.drive.DriveTestBase;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import edu.wpi.first.wpilibj.MockEncoder;

public class DriveForDistanceCommandTest extends DriveTestBase{
    LowBarScoreCommandGroup lowScoreCommands;

    @Before
    public void setup() {
        super.setup();
        this.lowScoreCommands = this.injector.getInstance(LowBarScoreCommandGroup.class);
    }
    
    @Test
    public void testDriveToLowGoal() {
        DriveToDistanceCommand driveForDistanceCommand = this.injector.getInstance(DriveToDistanceCommand.class);
        
        PoseSubsystem poseSubsystem = this.injector.getInstance(PoseSubsystem.class);
        
        driveForDistanceCommand.setTargetDistance(lowScoreCommands.distanceToLowGoal.get());
        
        ((MockEncoder)poseSubsystem.leftDriveEncoder).setDistance(0);
        
        driveForDistanceCommand.initialize();
        
        driveForDistanceCommand.execute();
       
        verifyGoingForward();
        
        ((MockEncoder)poseSubsystem.leftDriveEncoder).setDistance(lowScoreCommands.distanceToLowGoal.get());
        
        ((MockEncoder)poseSubsystem.rightDriveEncoder).setDistance(lowScoreCommands.distanceToLowGoal.get());
        
        driveForDistanceCommand.execute();
        
        driveForDistanceCommand.isFinished();
        
        driveForDistanceCommand.end();
        
        checkChassisPower(0, 0);
    }
}
