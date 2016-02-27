package competition.subsystems.hanger;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.hanger.hook_commands.HookExtendCommand;
import competition.subsystems.hanger.hook_commands.HookRetractCommand;
import competition.subsystems.hanger.hook_commands.HookStopCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristDownCommand;
import competition.subsystems.wrist.wrist_commands.MoveWristUpCommand;
import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class HookSubsystemTest extends BaseWPITest{
    
    HookExtendCommand extend;
    HookRetractCommand retract;
    HookStopCommand stop;
    
    HookSubsystem hook;
    
    @Before
    public void setup() {
        hook = injector.getInstance(HookSubsystem.class);
        
        extend = injector.getInstance(HookExtendCommand.class);
        retract = injector.getInstance(HookRetractCommand.class);
        stop = injector.getInstance(HookStopCommand.class);
    }
    
    @Test
    public void testStop() {
        stop.initialize();
        stop.execute();
        
        assertEquals(0, hook.hookMotor.get(), 0.001);
    }
    
    @Test
    public void testExtend() {
        extend.initialize();
        extend.execute();
        
        assertTrue(extend.hookExtentionPower.get() > 0);
        assertEquals(extend.hookExtentionPower.get(), hook.hookMotor.get(), 0.001); 
    }
    
    @Test
    public void testRetract() {
        retract.initialize();
        retract.execute();
        
        assertTrue(retract.hookRetractionPower.get() < 0);
        assertEquals(retract.hookRetractionPower.get(), hook.hookMotor.get(), 0.001); 
    }
    
    @Test
    public void testDistance() {
        assertEquals(0, hook.getHookDistance(), 0.001);
        ((MockEncoder)hook.hookEncoder).setDistance(100);
        assertEquals(100, hook.getHookDistance(), 0.001);
    }
}
