package competition.subsystems.arm.arm_commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class ArmManualControlCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    DoubleProperty lowerArmPower;
    OperatorInterface oi;

    @Inject
    public ArmManualControlCommand(ArmSubsystem armSubsystem, OperatorInterface oi, XPropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        this.oi = oi;
        this.requires(armSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double desire = -oi.operatorJoystick.getVector().y;
        // offset by range joystick kicks in
        if(desire > 0) {
            desire -= 0.15;
        } else {
            desire += 0.15;
        }
        
        // rescale back to -1 to 1 range
        desire = desire / 0.85;
        
        // square input
        desire = desire * Math.abs(desire);
        
        // and scale down
        desire = desire / 1.5;
        
        armSubsystem.setArmMotorPower(desire);
    }
    
    @Override
    public void end() {
        armSubsystem.setArmMotorPower(0);
    }
        
}