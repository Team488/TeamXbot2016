package competition.subsystems.arm;

import static org.junit.Assert.*;

import org.junit.Before;

import competition.BaseRobotTest;
import edu.wpi.first.wpilibj.MockDigitalInput;
import edu.wpi.first.wpilibj.MockEncoder;

public class ArmTestBase extends BaseRobotTest {
    
    protected ArmSubsystem arm;
    protected ArmTargetSubsystem armTarget;
    
    @Before
    public void setup() {
        this.arm = this.injector.getInstance(ArmSubsystem.class);
        this.armTarget = this.injector.getInstance(ArmTargetSubsystem.class);
        setLimitSwitches(false, false);
    }
    
    protected void setMockEncoder(double value) {
        ((MockEncoder)arm.encoder).setDistance(value);
    }
    
    protected void setLimitSwitches(boolean up, boolean down) {
        ((MockDigitalInput)arm.upperLimitSwitch).set_value(!up);
        ((MockDigitalInput)arm.lowerLimitSwitch).set_value(!down);
    }
    
    protected void verifyArmPower(double power) {
        assertEquals(power, arm.leftArmMotor.get(), 0.001);
        assertEquals(power, arm.rightArmMotor.get(), 0.001);
    }
    
    protected void verifyArmGoingUp() {
        assertTrue(arm.leftArmMotor.get() > 0 && arm.rightArmMotor.get() > 0);
    }
    
    protected void verifyArmGoingDown() {
        assertTrue(arm.leftArmMotor.get() < 0 && arm.rightArmMotor.get() < 0);
    }
}