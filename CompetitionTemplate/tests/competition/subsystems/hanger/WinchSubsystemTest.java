package competition.subsystems.hanger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.hanger.hook_commands.HookExtendCommand;
import competition.subsystems.hanger.hook_commands.HookRetractCommand;
import competition.subsystems.hanger.hook_commands.HookStopCommand;
import competition.subsystems.hanger.winch_commands.WinchExtendCommand;
import competition.subsystems.hanger.winch_commands.WinchRetractCommand;
import competition.subsystems.hanger.winch_commands.WinchStopCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristDownCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristUpCommand;
import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class WinchSubsystemTest extends BaseWPITest{
    
    WinchExtendCommand extend;
    WinchRetractCommand retract;
    WinchStopCommand stop;
    
    WinchSubsystem winch;
    
    @Before
    public void setup() {
        winch = injector.getInstance(WinchSubsystem.class);
        
        extend = injector.getInstance(WinchExtendCommand.class);
        retract = injector.getInstance(WinchRetractCommand.class);
        stop = injector.getInstance(WinchStopCommand.class);
    }
    
    @Test
    public void testStop() {
        stop.initialize();
        stop.execute();
        
        assertEquals(0, winch.winchMotor.get(), 0.001);
    }
    
    @Test
    public void testExtend() {
        extend.initialize();
        extend.execute();
        
        assertTrue(extend.winchExtentionPower.get() > 0);
        assertEquals(extend.winchExtentionPower.get(), winch.winchMotor.get(), 0.001); 
    }
    
    @Test
    public void testRetract() {
        retract.initialize();
        retract.execute();
        
        assertTrue(retract.winchRetractionPower.get() < 0);
        assertEquals(retract.winchRetractionPower.get(), winch.winchMotor.get(), 0.001); 
    }
    
    @Test
    public void testDistance() {
        assertEquals(0, winch.getWinchDistance(), 0.001);
        ((MockEncoder)winch.winchEncoder).setDistance(100);
        assertEquals(100, winch.getWinchDistance(), 0.001);
    }
}
