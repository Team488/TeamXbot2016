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
        double power = oi.operatorJoystick.getVector().y;
        armSubsystem.setArmMotorPower(power * Math.abs(power));
    }
    
    @Override
    public void end() {
        armSubsystem.setArmMotorPower(0);
    }
        
}