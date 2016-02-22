package competition.subsystems.arm.arm_commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.ArmTestBase;
import xbot.common.controls.sensors.MockJoystick;

public class ArmManualControlCommandTest extends ArmTestBase{
    OperatorInterface oi;
    
    @Before
    public void setup() {
        this.oi = this.injector.getInstance(OperatorInterface.class);
        super.setup();
    }
    
    @Test
    public void testArmManualContorlCommand() {
        ArmManualControlCommand armManualControl = this.injector.getInstance(ArmManualControlCommand.class);
        
        ((MockJoystick)oi.operatorJoystick).setY(0.5);
        
        armManualControl.execute();
        
        assertTrue(armSubsystem.rightArmMotor.get() == 0.25);
        
        ((MockJoystick)oi.operatorJoystick).setY(1);
        
        armManualControl.execute();
        
        assertTrue(armSubsystem.rightArmMotor.get() == 1);
        
        ((MockJoystick)oi.operatorJoystick).setY(-1);
        
        armManualControl.execute();
        
        assertTrue(armSubsystem.rightArmMotor.get() == -1);
        
        ((MockJoystick)oi.operatorJoystick).setY(0.75);
        
        armManualControl.execute();
        
        assertTrue(armSubsystem.rightArmMotor.get() == 0.5625);
        
    }
    
    
    
    

}
