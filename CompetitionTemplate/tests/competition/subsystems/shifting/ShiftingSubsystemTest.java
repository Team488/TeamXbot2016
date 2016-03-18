package competition.subsystems.shifting;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.BaseRobotTest;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.shifting.commands.ShiftLowCommand;
import xbot.common.injection.BaseWPITest;

public class ShiftingSubsystemTest extends BaseRobotTest {
    ShiftingSubsystem shiftingSubsystem;
    
    @Before
    public void setup() {
        this.shiftingSubsystem = this.injector.getInstance(ShiftingSubsystem.class);
    }
    
    @Test
    public void testShiftHighCommand() {
        ShiftHighCommand shiftHighCommand = this.injector.getInstance(ShiftHighCommand.class);
        
        shiftHighCommand.initialize();
        
        assertTrue(shiftingSubsystem.shifSolenoid.get());
    }
    
    @Test
    public void testShiftLowCommand() {
        ShiftLowCommand shiftLowCommand = this.injector.getInstance(ShiftLowCommand.class);
        
        shiftLowCommand.initialize();
        
        assertTrue(!(shiftingSubsystem.shifSolenoid.get()));
    }
}
