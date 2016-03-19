package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.hanger.HookPoseSubsystem;
import competition.subsystems.hanger.HookPositionTargetSubsystem;
import competition.subsystems.hanger.HookSubsystem;
import competition.subsystems.hanger.HookVelocityTargetSystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.XPropertyManager;

public class HookMaintainerCommand extends BaseCommand {
    
    private final HookSubsystem hook;
    private final HookPoseSubsystem pose;
    private final HookVelocityTargetSystem velocityTarget;
    private final HookPositionTargetSubsystem positionTarget;
    
    private final PIDManager velocityPID;
    private final PIDManager positionPID;
    
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
        positionPID = new PIDManager("HookPosition", propMan, 1.0, 0, 0, 100, -100);
    }

    @Override
    public void initialize() {
        velocityPID.reset();
        positionPID.reset();
    }

    @Override
    public void execute() {
        // general plan - get positional target from the subsystem, and compare to our current position.
        // use that output essentially as a velocity intent to give to a velocity PID.
        
        double positionGoal = positionTarget.getTargetPosition();
        double currentPosition = pose.getHookDistance();
        
        double goalVelocity = positionPID.calculate(positionGoal, currentPosition);
        double currentVelocity = pose.getHookVelocity();
        
        double power = velocityPID.calculate(goalVelocity, currentVelocity);
        
        hook.setHookMotorPower(power);
    }
    
    
}
