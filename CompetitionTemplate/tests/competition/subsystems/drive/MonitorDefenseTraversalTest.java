package competition.subsystems.drive;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import competition.subsystems.drive.PoseSubsystem.DefenseState;
import competition.subsystems.drive.commands.MonitorDefenseTraversalModule;
import edu.wpi.first.wpilibj.MockTimer;

public class MonitorDefenseTraversalTest extends DriveTestBase {
        
    MonitorDefenseTraversalModule module;
    MockTimer timer;
    
    @Before
    public void setup() {
        super.setup();
        timer = injector.getInstance(MockTimer.class);
        module = injector.getInstance(MonitorDefenseTraversalModule.class);
    }
    
    @Test
    public void testStartNotOnDefense() {
        assertTrue(module.measureState(0) == DefenseState.NotOnDefense);
    }
    
    @Test
    public void testSuddenTilt() {
        assertTrue(module.measureState(50) == DefenseState.OnDefense);
    }
    
    @Test
    public void testTiltFollowedByFlat() {
        assertTrue(module.measureState(-50) == DefenseState.OnDefense);
        assertTrue(module.measureState(-50) == DefenseState.RecentlyOnDefense);
    }
    
    @Test
    public void testTiltWaitThenFlat() {
        assertTrue(module.measureState(-50) == DefenseState.OnDefense);
        timer.advanceTimeInSecondsBy(100);
        assertTrue(module.measureState(0) == DefenseState.NotOnDefense);
    }
    
    @Test
    public void testTiltWaitFlatWaitTiltWait() {
        testTiltWaitThenFlat();
        testTiltWaitThenFlat();
    }
}
