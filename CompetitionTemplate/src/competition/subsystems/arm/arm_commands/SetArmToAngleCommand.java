package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;

public class SetArmToAngleCommand extends BaseCommand{

    protected ArmTargetSubsystem armTarget;
    protected double goalAngle;
    
    @Inject
    public SetArmToAngleCommand(ArmTargetSubsystem armTarget) {
        this.armTarget = armTarget;
    }
    
    public void setGoalAngle(double angle) {
        goalAngle = angle;
    }
    
    @Override
    public void initialize() {
        armTarget.setTargetAngle(goalAngle);
    }

    @Override
    public void execute() {
        // nothing happens here.
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
