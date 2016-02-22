package competition.subsystems.arm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import competition.BaseRobotTest;
import edu.wpi.first.wpilibj.MockDigitalInput;
import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class ArmSubsystemTest extends ArmTestBase {
        
    @Before
    public void setup() {
        super.setup();
    }
    
    @Test
    public void limitSwitchTest() {
        ((MockDigitalInput)armSubsystem.upperLimitSwitch).set_value(true); //mocks the upper limit switch being pressed
        
        assertTrue(armSubsystem.isArmAtMaximumHeight()); //say true if the arm is at maximum height
        
        ((MockDigitalInput)armSubsystem.lowerLimitSwitch).set_value(true); //mocks the lower limit switch being pressed
        
        assertTrue(armSubsystem.isArmAtMinimumHeight()); //say true is the arm is at minimum height
    }
    
    @Test
    @Ignore("Calibration currently disabled")
    public void testCalibration() {
        setMockEncoder(0);
        assertEquals(0, armSubsystem.getArmAngle(), 0.001);
        
        setMockEncoder(-100);
        assertEquals(-100, armSubsystem.getArmAngle(), 0.001);
        
        setLimitSwitches(false, true);
        armSubsystem.updateSensors();
        
        assertEquals(0, armSubsystem.getArmAngle(), 0.001);
        
        setMockEncoder(0);
        assertEquals(100, armSubsystem.getArmAngle(), 0.001);
    }
}
