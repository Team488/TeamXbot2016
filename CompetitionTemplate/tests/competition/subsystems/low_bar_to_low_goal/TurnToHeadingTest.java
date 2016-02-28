package competition.subsystems.low_bar_to_low_goal;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.autonomous.LowBarScoreCommandGroup;
import competition.subsystems.autonomous.TurnToHeadingCommand;
import competition.subsystems.drive.DriveTestBase;

public class TurnToHeadingTest extends DriveTestBase{
    LowBarScoreCommandGroup lowScoreCommands;
    
    @Before
    public void setup() {
        super.setup();
        this.lowScoreCommands = this.injector.getInstance(LowBarScoreCommandGroup.class);
    }
    
    @Test
    public void testTurnToHeadingCommand(){
        TurnToHeadingCommand turnToHeadingCommand = this.injector.getInstance(TurnToHeadingCommand.class);
        
        turnToHeadingCommand.setTargetHeading(lowScoreCommands.headingToFaceLowGoal.get());
        
        turnToHeadingCommand.turnRight(false);
        
        setMockGyroHeading(0);
        
        turnToHeadingCommand.execute();
        
        verifyTurningLeft();
        
        turnToHeadingCommand.setTargetHeading(lowScoreCommands.headingToFaceLowGoal.get());
        
        turnToHeadingCommand.turnRight(true);
        
        setMockGyroHeading(0);
        
        turnToHeadingCommand.execute();
        
        verifyTurningRight();
        
        setMockGyroHeading(lowScoreCommands.headingToFaceLowGoal.get());
        
        turnToHeadingCommand.execute();
        
        turnToHeadingCommand.isFinished();
        
        turnToHeadingCommand.end();
        
        checkChassisPower(0, 0);
    }
}
