package competition.subsystems.hanger;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.hanger.winch_commands.WinchFollowHookProportionallyCommand;

import static org.junit.Assert.*;

import edu.wpi.first.wpilibj.MockEncoder;
import xbot.common.injection.BaseWPITest;

public class ScalingTest extends BaseWPITest {

    HookSubsystem hook;
    WinchSubsystem winch;
    
    WinchFollowHookProportionallyCommand follow;
    
    @Before
    public void setup() {
        super.setUp();
        
        hook = injector.getInstance(HookSubsystem.class);
        winch = injector.getInstance(WinchSubsystem.class);
        
        follow = injector.getInstance(WinchFollowHookProportionallyCommand.class);
    }
    
    private void setEncoders(double hookDistance, double winchDistance) {
        ((MockEncoder)hook.hookEncoder).setDistance(hookDistance);
        ((MockEncoder)winch.winchEncoder).setDistance(winchDistance);
    }
    
    private void verifyWinchGoingUp() {
        assertTrue(winch.winchMotor.get() > 0);
    }
    
    private void verifyWinchGoingDown() {
        assertTrue(winch.winchMotor.get() < 0);
    }
    
    private void verifyWinchStopped() {
        assertEquals(0, winch.winchMotor.get(), 0.001);
    }
    
    @Test
    public void testWinchFollowingHook() {
        verifyWinchStopped();
        
        // with both at 0, the follow command should let out a little slack.
        follow.initialize();
        follow.execute();
        
        verifyWinchGoingUp();
        
        // now that the winch has gone too far, it should retract
        setEncoders(0, 20);
        follow.execute();
        verifyWinchGoingDown();
        
        //let's move the hook further out
        setEncoders(100, 20);
        follow.execute();
        verifyWinchGoingUp();
    }
    
}
