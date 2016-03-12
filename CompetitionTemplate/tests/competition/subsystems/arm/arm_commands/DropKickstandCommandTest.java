package competition.subsystems.arm.arm_commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmTestBase;
import edu.wpi.first.wpilibj.MockTimer;

public class DropKickstandCommandTest extends ArmTestBase {

    DropKickstandCommand command;
    
    @Before
    public void setup() {
        super.setup();
        
        command = injector.getInstance(DropKickstandCommand.class);
    }
    
    @Test
    public void testUncalibratedStart() {
        verifyArmPower(0);
        
        command.initialize();
        
        command.execute();
        verifyArmGoingUp();
        
        setMockEncoder(10);
        command.execute();
        verifyArmPower(0);
        
        setMockEncoder(1);
        command.execute();
        verifyArmGoingUp();
        
        MockTimer timer = injector.getInstance(MockTimer.class);
        timer.advanceTimeInSecondsBy(10);
        assertTrue(command.isFinished());    
        command.execute();
        
        verifyArmPower(0);
    }
    
    @Test
    public void testCalibratedStart() {
        arm.calibrateCurrentPositionAsLow();
        
        command.initialize();
        
        command.execute();
        verifyArmGoingUp();
        
        // is now a valid place to be, so no stopping
        setMockEncoder(10);
        command.execute();
        verifyArmGoingUp();
        
        // trigger natural protection
        setMockEncoder(100);
        command.execute();
        verifyArmPower(0);
        
        setMockEncoder(1);
        command.execute();
        verifyArmGoingUp();
        
        MockTimer timer = injector.getInstance(MockTimer.class);
        timer.advanceTimeInSecondsBy(10);
        assertTrue(command.isFinished());    
        command.execute();
        
        verifyArmPower(0);
        
    }
}
