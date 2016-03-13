package competition.subsystems.arm.arm_commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmTestBase;
import edu.wpi.first.wpilibj.MockTimer;

public class SetArmToAngleCommandTest extends ArmTestBase {

    private SetArmToAngleCommand command;
    
    @Before
    public void setup() {
        super.setup();
        command = injector.getInstance(SetArmToAngleCommand.class);
    }
    
    @Test
    public void testSetAngle() {
        command.setGoalAngle(40);
        command.initialize();
        
        assertEquals(40, armTarget.getTargetAngle(), 0.001);
    }
    
    @Test
    public void testOutOfBounds() {
        command.setGoalAngle(100);
        command.initialize();
        
        assertEquals(90, armTarget.getTargetAngle(), 0.001);
        
        command.setGoalAngle(-10);
        command.initialize();
        
        assertEquals(0, armTarget.getTargetAngle(), 0.001);
    }
    
}