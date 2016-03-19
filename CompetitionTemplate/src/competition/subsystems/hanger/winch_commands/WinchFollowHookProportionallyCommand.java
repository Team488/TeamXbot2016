package competition.subsystems.hanger.winch_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookPoseSubsystem;
import competition.subsystems.hanger.HookSubsystem;
import competition.subsystems.hanger.WinchSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class WinchFollowHookProportionallyCommand extends BaseCommand{

    HookSubsystem hook;
    HookPoseSubsystem hookPose;
    WinchSubsystem winch;
    
    final DoubleProperty winchFollowHookOffset;
    final DoubleProperty winchFollowHookRatio;
    
    final PIDManager winchFollowPidManager;
    
    @Inject
    public WinchFollowHookProportionallyCommand(
            HookSubsystem hook, 
            HookPoseSubsystem hookPose,
            WinchSubsystem winch, 
            XPropertyManager propMan) {
        this.hook = hook;
        this.winch = winch;
        this.hookPose = hookPose;
        
        winchFollowHookOffset = propMan.createPersistentProperty("WinchFollowHookOffset", 5.0);
        winchFollowHookRatio = propMan.createPersistentProperty("WinchFollowHookRatio", 1.0);
        
        winchFollowPidManager = new PIDManager("WinchFollow", propMan, 0.05, 0, 0);
        
        // This doesn't need the hook - it just reads the hook, never sets it.
        this.requires(winch);
    }

    @Override
    public void initialize() {
        winchFollowPidManager.reset();        
    }

    @Override
    public void execute() {
        // where is the hook?
        double hookPosition = hookPose.getHookDistance();
        double targetWinchPosition = 
                (hookPosition * winchFollowHookRatio.get()) + winchFollowHookOffset.get(); 
        // where is the winch?
        double currentWinchPosition = winch.getWinchDistance();
        
        // figure out where we want the winch to be, in relation to the hook
        double power = winchFollowPidManager.calculate(targetWinchPosition, currentWinchPosition);
        
        // follow the hook!
        winch.setWinchMotorPower(power);
    }
    
    @Override
    public void end() {
        hook.setHookMotorPower(0);
    }
}
