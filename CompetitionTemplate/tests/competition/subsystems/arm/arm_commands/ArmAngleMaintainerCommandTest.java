package competition.subsystems.arm.arm_commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmTestBase;
import edu.wpi.first.wpilibj.MockTimer;

public class ArmAngleMaintainerCommandTest extends ArmTestBase {
    ArmAngleMaintainerCommand angleMaintainer;
    WaitForArmCalibrationCommand waitForCalibrate;

    @Before
    public void setup() {
        super.setup();
        angleMaintainer = this.injector.getInstance(ArmAngleMaintainerCommand.class);
        waitForCalibrate = this.injector.getInstance(WaitForArmCalibrationCommand.class);
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
        setLimitSwitches(false, false);
        
        armTargetSubsystem.setTargetAngle(0);
        
        angleMaintainer.execute();

        assertTrue(armSubsystem.leftArmMotor.get() < 0);
        
        assertTrue(armSubsystem.rightArmMotor.get() < 0);
    }
    
    @Test
    public void testAutoCalibration() {
        
        assertTrue(!waitForCalibrate.isFinished());
        
        angleMaintainer.setAutoCalibration(true);
        angleMaintainer.initialize();
        armTargetSubsystem.setTargetAngle(90);
        angleMaintainer.execute();
        
        // arm trying to go down and calibrate
        assertTrue(armSubsystem.leftArmMotor.get() < 0);
        
        // set up calibration conditions
        setLimitSwitches(false, true);
        
        assertTrue(waitForCalibrate.isFinished());
        
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
        
        assertTrue(!waitForCalibrate.isFinished());
        
        // arm trying to go down and calibrate
        assertTrue(armSubsystem.leftArmMotor.get() < 0);
        
        MockTimer timer = injector.getInstance(MockTimer.class);
        timer.setTimeInSeconds(10);
        
        angleMaintainer.execute();
        // should be going up as normal now
        assertEquals(0, armSubsystem.leftArmMotor.get(), 0.001);
        
        assertTrue(!waitForCalibrate.isFinished());
    }

}
