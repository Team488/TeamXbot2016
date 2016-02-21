package competition.subsystems.wrist;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.wrist.wrist_commands.MoveWristDownCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristUpCommand;
import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class WristSubsystemTest extends BaseWPITest{
    WristSubsystem wristSubsystem;
    ArmSubsystem arm;
    
    @Before
    public void setup() {
        this.wristSubsystem = this.injector.getInstance(WristSubsystem.class);
        this.arm = this.injector.getInstance(ArmSubsystem.class);
    }
    
    @Test
    public void moveWristUpCommand() {
        MoveWristUpCommand moveWristUpCommand = this.injector.getInstance(MoveWristUpCommand.class);
        
        // move the arm out of the danger zone
        ((MockEncoder)arm.encoder).setDistance(1000);
        moveWristUpCommand.execute();
        
        assertTrue(wristSubsystem.solenoidA.get());
        
        assertTrue(moveWristUpCommand.isFinished());
    }
    
    @Test
    public void tryMoveWristUpInDangerZone() {
MoveWristUpCommand moveWristUpCommand = this.injector.getInstance(MoveWristUpCommand.class);
        
        // move the arm out of the danger zone
        moveWristUpCommand.enableWristLegalityProtection.set(true);
        moveWristUpCommand.execute();
        
        assertTrue(!wristSubsystem.solenoidA.get());
        
        assertTrue(moveWristUpCommand.isFinished());
    }
    
    @Test
    public void moveWristDownCommand() {
        MoveWristDownCommand moveWristDownCommand = this.injector.getInstance(MoveWristDownCommand.class);
        
        moveWristDownCommand.initialize();
        
        assertTrue(!(wristSubsystem.solenoidA.get()));
        
        assertTrue(moveWristDownCommand.isFinished());
    }

}
