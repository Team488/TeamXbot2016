package competition.subsystems.arm;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.arm_commands.LowerArmCommand;
import competition.subsystems.arm.arm_commands.RaiseArmCommand;
import edu.wpi.first.wpilibj.MockDigitalInput;
import xbot.common.injection.BaseWPITest;

public class ArmSubsystemTest extends BaseWPITest{
    
    ArmSubsystem armSubsystem;
    
    @Before
    public void setup() {
        this.armSubsystem = this.injector.getInstance(ArmSubsystem.class);

    }
    
    @Test
    public void testRaiseArmCommand() {
        RaiseArmCommand raiseArmCommand = this.injector.getInstance(RaiseArmCommand.class);
        
        assertTrue(armSubsystem.leftArmMotor.get() == 0 && armSubsystem.rightArmMotor.get() == 0);
        
        raiseArmCommand.initialize();
        
        raiseArmCommand.execute();
        
        assertTrue(armSubsystem.leftArmMotor.get() > 0 && armSubsystem.rightArmMotor.get() > 0);
        
        raiseArmCommand.end();
        
        assertTrue(armSubsystem.leftArmMotor.get() == 0 && armSubsystem.rightArmMotor.get() == 0);
    }
    
    @Test
    public void testLowerArmCommand(){
        LowerArmCommand lowerArmCommand = this.injector.getInstance(LowerArmCommand.class); //black magic that says to test this class
        
        assertTrue(armSubsystem.leftArmMotor.get() == 0 && armSubsystem.rightArmMotor.get() == 0); //at the beginning, the motor powers should be 0. Say true if the motors are 0
        
        lowerArmCommand.initialize(); //runs initialize in lowerArmCommand
        
        lowerArmCommand.execute(); //runs execute in the lowerArmCommand
        
        assertTrue(armSubsystem.leftArmMotor.get() < 0 && armSubsystem.rightArmMotor.get() < 0); //the motor power should be greater than 0 after initialize and execute runs
        
        lowerArmCommand.end();
        
        assertTrue(armSubsystem.leftArmMotor.get() == 0 && armSubsystem.rightArmMotor.get() == 0); //the motor power should be 0 after end. if so, report true
    }
    
    @Test
    public void limitSwitchTest() {
        ((MockDigitalInput)armSubsystem.upperLimitSwitch).set_value(true); //mocks the upper limit switch being pressed
        
        assertTrue(armSubsystem.isArmAtMaximumHeight()); //say true if the arm is at maximum height
        
        ((MockDigitalInput)armSubsystem.lowerLimitSwitch).set_value(true); //mocks the lower limit switch being pressed
        
        assertTrue(armSubsystem.isArmAtMinimumHeight()); //say true is the arm is at minimum height
    }
}
