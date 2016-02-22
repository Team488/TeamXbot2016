package competition.subsystems.arm.arm_commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import edu.wpi.first.wpilibj.MockDigitalInput;
import edu.wpi.first.wpilibj.MockEncoder;
import edu.wpi.first.wpilibj.MockTimer;
import edu.wpi.first.wpilibj.Timer;
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
        armSubsystem.calibrateCurrentPositionAsLow();
        setMockEncoder(0);
        
        armTargetSubsystem.setTargetAngle(90);
        
        angleMaintainer.execute();
        
        assertTrue(armSubsystem.leftArmMotor.get() > 0);
        
        assertTrue(armSubsystem.rightArmMotor.get() > 0);
    }
    
    @Test
    public void testArmAngle90to0() {
        armSubsystem.calibrateCurrentPositionAsLow();
        setMockEncoder(90);
        
        armTargetSubsystem.setTargetAngle(0);
        
        angleMaintainer.execute();

        assertTrue(armSubsystem.leftArmMotor.get() < 0);
        
        assertTrue(armSubsystem.rightArmMotor.get() < 0);
    }
    
    @Test
    @Ignore("Calibration currently disabled")
    public void testAutoCalibration() {
        angleMaintainer.setAutoCalibration(true);
        angleMaintainer.initialize();
        armTargetSubsystem.setTargetAngle(90);
        angleMaintainer.execute();
        
        // arm trying to go down and calibrate
        assertTrue(armSubsystem.leftArmMotor.get() < 0);
        
        // set up calibration conditions
        setLimitSwitches(false, true);
        
        angleMaintainer.execute();
        // should be going up as normal now
        assertTrue(armSubsystem.leftArmMotor.get() > 0);
    }
    
    @Test
    public void testAutoCalibrationTimeout() {
        angleMaintainer.setAutoCalibration(true);
        armTargetSubsystem.setTargetAngle(90);
        angleMaintainer.initialize();
        angleMaintainer.execute();
        
        // arm trying to go down and calibrate
        assertTrue(armSubsystem.leftArmMotor.get() < 0);
        
        MockTimer timer = injector.getInstance(MockTimer.class);
        timer.setTimeInSeconds(10);
        
        angleMaintainer.execute();
        // should be going up as normal now
        assertEquals(0, armSubsystem.leftArmMotor.get(), 0.001);
    }

}
