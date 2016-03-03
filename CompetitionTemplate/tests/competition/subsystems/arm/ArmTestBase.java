package competition.subsystems.arm;

import static org.junit.Assert.*;

import org.junit.Before;

import competition.BaseRobotTest;
import edu.wpi.first.wpilibj.MockDigitalInput;
import edu.wpi.first.wpilibj.MockEncoder;

public class ArmTestBase extends BaseRobotTest {
    
    protected ArmSubsystem armSubsystem;
    protected ArmTargetSubsystem armTargetSubsystem;
    
    @Before
    public void setup() {
        this.armSubsystem = this.injector.getInstance(ArmSubsystem.class);
        this.armTargetSubsystem = this.injector.getInstance(ArmTargetSubsystem.class);
        setLimitSwitches(false, false);
    }
    
    protected void setMockEncoder(double value) {
        ((MockEncoder)armSubsystem.encoder).setDistance(value);
    }
    
    protected void setLimitSwitches(boolean up, boolean down) {
        ((MockDigitalInput)armSubsystem.upperLimitSwitch).set_value(!up);
        ((MockDigitalInput)armSubsystem.lowerLimitSwitch).set_value(!down);
    }
    
    protected void verifyArmPower(double power) {
        assertEquals(power, armSubsystem.leftArmMotor.get(), 0.001);
        assertEquals(power, armSubsystem.rightArmMotor.get(), 0.001);
    }
    
    protected void verifyArmGoingUp() {
        assertTrue(armSubsystem.leftArmMotor.get() > 0 && armSubsystem.rightArmMotor.get() > 0);
    }
    
    protected void verifyArmGoingDown() {
        assertTrue(armSubsystem.leftArmMotor.get() < 0 && armSubsystem.rightArmMotor.get() < 0);
    }
}