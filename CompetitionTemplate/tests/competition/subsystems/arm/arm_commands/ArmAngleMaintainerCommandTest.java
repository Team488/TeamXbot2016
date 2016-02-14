package competition.subsystems.arm.arm_commands;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class ArmAngleMaintainerCommandTest extends BaseWPITest {
    ArmAngleMaintainerCommand angleMaintainer;
    ArmTargetSubsystem armTargetSubsystem;

    @Before
    public void setup() {
        angleMaintainer = this.injector.getInstance(ArmAngleMaintainerCommand.class);

        armTargetSubsystem = this.injector.getInstance(ArmTargetSubsystem.class);
    }

    @Test
    public void testArmAngle0to90() {
        ArmSubsystem armSubsystem = this.injector.getInstance(ArmSubsystem.class);
        
        ((MockEncoder)armSubsystem.encoder).setDistance(0);
        
        armTargetSubsystem.setTargetAngle(90);
        
        angleMaintainer.execute();
        
        assertEquals(1.0, armSubsystem.armMotor1.get(), 0.001);
        
        assertEquals(1.0, armSubsystem.armMotor2.get(), 0.001);
    }
    
    @Test
    public void testArmAngle90to0() {
        ArmSubsystem armSubsystem = this.injector.getInstance(ArmSubsystem.class);
        
        ((MockEncoder)armSubsystem.encoder).setDistance(90);
        
        armTargetSubsystem.setTargetAngle(0);
        
        angleMaintainer.execute();

        assertEquals(-1.0, armSubsystem.armMotor1.get(), 0.001);
        
        assertEquals(-1.0, armSubsystem.armMotor2.get(), 0.001);
    }

}
