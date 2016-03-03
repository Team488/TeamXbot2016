package competition.subsystems.arm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import competition.BaseRobotTest;
import competition.subsystems.arm.arm_commands.LowerArmCommand;
import competition.subsystems.arm.arm_commands.RaiseArmCommand;
import edu.wpi.first.wpilibj.MockDigitalInput;
import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class ArmSubsystemTest extends ArmTestBase {
        
    @Before
    public void setup() {
        super.setup();
    }
    
    @Test
    public void testRaiseArmCommand() {
        RaiseArmCommand raiseArmCommand = this.injector.getInstance(RaiseArmCommand.class);
        
        verifyArmPower(0);
        
        raiseArmCommand.initialize();
        
        raiseArmCommand.execute();
        
        verifyArmGoingUp();
        
        raiseArmCommand.end();
        
        verifyArmPower(0);
    }
    
    @Test
    public void testLowerArmCommand(){
        LowerArmCommand lowerArmCommand = this.injector.getInstance(LowerArmCommand.class); //black magic that says to test this class
        
       //at the beginning, the motor powers should be 0. Say true if the motors are 0
        verifyArmPower(0); 
        
        lowerArmCommand.initialize(); //runs initialize in lowerArmCommand
        lowerArmCommand.execute(); //runs execute in the lowerArmCommand
        
        //the motor power should be less than 0 after initialize and execute runs
        verifyArmGoingDown(); 
        
        lowerArmCommand.end();
        
        verifyArmPower(0);
    }
    
    @Test
    public void limitSwitchTest() {
        ((MockDigitalInput)armSubsystem.upperLimitSwitch).set_value(false); //mocks the upper limit switch being pressed
        
        assertTrue(armSubsystem.isArmAtMaximumHeight()); //say true if the arm is at maximum height
        
        ((MockDigitalInput)armSubsystem.lowerLimitSwitch).set_value(false); //mocks the lower limit switch being pressed
        
        assertTrue(armSubsystem.isArmAtMinimumHeight()); //say true is the arm is at minimum height
    }
    
    @Test
    public void maxAngleHeightTest(){
        setMockEncoder(45);
        
        armSubsystem.setArmMotorPower(1.0);
        verifyArmPower(1);
        
        setMockEncoder(100);
        armSubsystem.setArmMotorPower(1.0);
        
        assertTrue(armSubsystem.isArmAtMaxAngleHeight());
        verifyArmPower(0);
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
