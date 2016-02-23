package competition.subsystems.drive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.drive.commands.TraverseDefenseCommand;
import edu.wpi.first.wpilibj.MockTimer;
import xbot.common.injection.BaseWPITest;

public class TraverseDefenseCommandTest extends BaseWPITest {
    
    TraverseDefenseCommand command;
    
    MockTimer timer;
    
    @Before
    public void setUp() {
        super.setUp();
        
        this.command = this.injector.getInstance(TraverseDefenseCommand.class);
        this.timer = this.injector.getInstance(MockTimer.class);
        
        this.command.setPower(0.5);
        this.command.setTimeLimits(1, 2);
    }
    
    @Test
    public void testTimingOut() {
        
        command.initialize();
        assertFalse(command.isFinished());
        
        this.timer.setTimeInSeconds(2.5);
        
        assertTrue(command.isFinished());
        
    }
}
