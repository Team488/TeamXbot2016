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
        arm.calibrateCurrentPositionAsLow();
        setMockEncoder(0);
        
        angleMaintainer.execute();
        
        armTarget.setTargetAngle(90);
        
        angleMaintainer.execute();
        
        assertTrue(arm.leftArmMotor.get() > 0);
        
        assertTrue(arm.rightArmMotor.get() > 0);
    }
    
    @Test
    public void testArmAngle90to0() {
        arm.calibrateCurrentPositionAsLow();
        setMockEncoder(90);
        setLimitSwitches(false, false);
        
        armTarget.setTargetAngle(0);
        
        angleMaintainer.execute();

        assertTrue(arm.leftArmMotor.get() < 0);
        
        assertTrue(arm.rightArmMotor.get() < 0);
    }
    
    @Test
    public void testAutoCalibration() {
        
        assertTrue(!waitForCalibrate.isFinished());
        
        angleMaintainer.setAutoCalibration(true);
        angleMaintainer.initialize();
        armTarget.setTargetAngle(90);
        angleMaintainer.execute();
        
        // arm trying to go down and calibrate
        assertTrue(arm.leftArmMotor.get() < 0);
        
        // set up calibration conditions
        setLimitSwitches(false, true);
        
        assertTrue(waitForCalibrate.isFinished());
        
        angleMaintainer.execute();

        armTarget.setTargetAngle(90);
        
        angleMaintainer.execute();
        // should be going up as normal now
        assertTrue(arm.leftArmMotor.get() > 0);
    }
    
    @Test
    public void testAutoCalibrationTimeout() {
        angleMaintainer.setAutoCalibration(true);
        armTarget.setTargetAngle(90);
        
        angleMaintainer.execute();
        
        assertTrue(!waitForCalibrate.isFinished());
        assertTrue(!angleMaintainer.hasGivenUpCalibration());
        
        // arm trying to go down and calibrate
        assertTrue(arm.leftArmMotor.get() < 0);
        
        MockTimer timer = injector.getInstance(MockTimer.class);
        timer.setTimeInSeconds(10);
        
        angleMaintainer.execute();
        angleMaintainer.execute();
        angleMaintainer.execute();
        // should be going up as normal now, since it gave up
        verifyArmPower(0.0);
        
        assertTrue(!waitForCalibrate.isFinished());
        assertTrue(angleMaintainer.hasGivenUpCalibration());
    }
    
    @Test
    public void testMaintainerWorksAfterCalibrationTimeout() {
        angleMaintainer.setAutoCalibration(true);
        armTarget.setTargetAngle(90);
        
        angleMaintainer.execute();
        
        // arm trying to go down and calibrate
        assertTrue(arm.leftArmMotor.get() < 0);
        
        MockTimer timer = injector.getInstance(MockTimer.class);
        timer.setTimeInSeconds(10);
        
        angleMaintainer.execute();
        angleMaintainer.execute();
        
        assertTrue(!waitForCalibrate.isFinished());
        assertTrue(angleMaintainer.hasGivenUpCalibration());
        
        angleMaintainer.execute();
        verifyArmPower(0);
    }

}
