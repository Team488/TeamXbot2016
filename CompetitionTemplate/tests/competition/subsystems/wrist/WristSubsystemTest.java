package competition.subsystems.wrist;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.wrist.wrist_commands.MoveWristDownCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristUpCommand;
import xbot.common.injection.BaseWPITest;

public class WristSubsystemTest extends BaseWPITest{
    WristSubsystem wristSubsystem;
    
    @Before
    public void setup() {
        this.wristSubsystem = this.injector.getInstance(WristSubsystem.class);
    }
    
    @Test
    public void moveWristUpCommand() {
        MoveWristUpCommand moveWristUpCommand = this.injector.getInstance(MoveWristUpCommand.class);
        
        moveWristUpCommand.initialize();
        
        assertTrue(wristSubsystem.solenoid.get());
        
        assertTrue(moveWristUpCommand.isFinished());
    }
    
    @Test
    public void moveWristDownCommand() {
        MoveWristDownCommand moveWristDownCommand = this.injector.getInstance(MoveWristDownCommand.class);
        
        moveWristDownCommand.initialize();
        
        assertTrue(!(wristSubsystem.solenoid.get()));
        
        assertTrue(moveWristDownCommand.isFinished());
    }

}
