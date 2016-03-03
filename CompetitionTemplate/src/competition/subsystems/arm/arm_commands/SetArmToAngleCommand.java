package competition.subsystems.arm.arm_commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import javafx.beans.property.DoubleProperty;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class SetArmToAngleCommand extends BaseCommand{

    protected ArmTargetSubsystem armTarget;
    protected ArmSubsystem arm;
    protected double goalAngle;
    
    private static Logger log = Logger.getLogger(SetArmToAngleCommand.class);
    
    @Inject
    public SetArmToAngleCommand(ArmTargetSubsystem armTarget, ArmSubsystem arm) {
        this.armTarget = armTarget;
        this.arm = arm;
        this.requires(armTarget);
    }
    
    public void setGoalAngle(double angle) {
        
        double minValue = 0;
        double maxValue = arm.getArmMaximumSafeAngle();
        
        if (angle < minValue || angle > maxValue) {
            log.warn("Goal angle of " + angle 
                    + " was outside of the range [" + minValue + "," + maxValue + "]!");
        }
        
        angle = MathUtils.constrainDouble(angle, minValue, maxValue);        
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
