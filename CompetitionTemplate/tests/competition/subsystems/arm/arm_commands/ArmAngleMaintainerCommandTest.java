package competition.subsystems.arm.arm_commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import edu.wpi.first.wpilibj.MockDigitalInput;
import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class ArmAngleMaintainerCommandTest extends BaseWPITest {
    ArmAngleMaintainerCommand angleMaintainer;
    ArmTargetSubsystem armTargetSubsystem;
    ArmSubsystem armSubsystem;

    @Before
    public void setup() {
        angleMaintainer = this.injector.getInstance(ArmAngleMaintainerCommand.class);
        armSubsystem = this.injector.getInstance(ArmSubsystem.class);
        armTargetSubsystem = this.injector.getInstance(ArmTargetSubsystem.class);
    }
    
    private void setMockEncoder(double value) {
        ((MockEncoder)armSubsystem.encoder).setDistance(value);
    }
    
    private void setLimitSwitches(boolean up, boolean down) {
        ((MockDigitalInput)armSubsystem.upperLimitSwitch).set_value(up);
        ((MockDigitalInput)armSubsystem.lowerLimitSwitch).set_value(down);
    }

    @Test
    public void testArmAngle0to90() {
        armSubsystem.forceCalibrateLow();
        setMockEncoder(0);
        
        armTargetSubsystem.setTargetAngle(90);
        
        angleMaintainer.execute();
        
        assertTrue(armSubsystem.leftArmMotor.get() > 0);
        
        assertTrue(armSubsystem.rightArmMotor.get() > 0);
    }
    
    @Test
    public void testArmAngle90to0() {
        armSubsystem.forceCalibrateLow();
        setMockEncoder(90);
        
        armTargetSubsystem.setTargetAngle(0);
        
        angleMaintainer.execute();

        assertTrue(armSubsystem.leftArmMotor.get() < 0);
        
        assertTrue(armSubsystem.rightArmMotor.get() < 0);
    }
    
    @Test
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
