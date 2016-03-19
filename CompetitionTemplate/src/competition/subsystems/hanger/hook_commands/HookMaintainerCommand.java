package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookPoseSubsystem;
import competition.subsystems.hanger.HookPositionTargetSubsystem;
import competition.subsystems.hanger.HookSubsystem;
import competition.subsystems.hanger.HookVelocityTargetSystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class HookMaintainerCommand extends BaseCommand {
    
    private final HookSubsystem hook;
    private final HookPoseSubsystem pose;
    private final HookVelocityTargetSystem velocityTarget;
    private final HookPositionTargetSubsystem positionTarget;
    
    private final PIDManager velocityPID;
    private final PIDManager positionPID;
    
    private final DoubleProperty maxHookVelocity;
    private final DoubleProperty maxHookThrottle;
    
    private double throttle;
    
    @Inject
    public HookMaintainerCommand(XPropertyManager propMan,
            HookSubsystem hook,
            HookPoseSubsystem pose,
            HookVelocityTargetSystem velocityTarget,
            HookPositionTargetSubsystem positionTarget) {
        this.hook = hook;
        this.pose = pose;
        this.velocityTarget = velocityTarget;
        this.positionTarget = positionTarget;
        
        this.requires(hook);
        
        velocityPID = new PIDManager("HookVelocity", propMan, 1.0, 0, 0);
        positionPID = new PIDManager("HookPosition", propMan, 1.0, 0, 0);
        
        maxHookVelocity = propMan.createPersistentProperty("MaxHookVelocity", 5.0);
        maxHookThrottle = propMan.createPersistentProperty("MaxHookThrottle", 1.0);
    }

    @Override
    public void initialize() {
        velocityPID.reset();
        positionPID.reset();
        
        this.positionTarget.setTargetPosition(pose.getHookDistance());
    }

    @Override
    public void execute() {
        // general plan - get positional target from the subsystem, and compare to our current position.
        // use that output essentially as a velocity intent to give to a velocity PID.
        
        double positionGoal = positionTarget.getTargetPosition();
        double currentPosition = pose.getHookDistance();
        
        double goalVelocity = positionPID.calculate(positionGoal, currentPosition);
        double adjustedGoalVelocity = goalVelocity * maxHookVelocity.get();
        
        double currentVelocity = pose.getHookVelocity();
        
        double power = velocityPID.calculate(adjustedGoalVelocity, currentVelocity);
        
        throttle += power;
        throttle = MathUtils.constrainDouble(throttle, -maxHookThrottle.get(), maxHookThrottle.get());
        hook.setHookMotorPower(throttle);
    }
    
    
}
