package competition.subsystems.arm.arm_commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;

public class ArmManualControlCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    DoubleProperty lowerArmPower;
    OperatorInterface oi;

    @Inject
    public ArmManualControlCommand(ArmSubsystem armSubsystem, OperatorInterface oi, PropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        this.oi = oi;
        this.requires(armSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        armSubsystem.setArmMotorPower(oi.operatorJoystick.getVector().y);
    }
    
    @Override
    public void end() {
        armSubsystem.setArmMotorPower(0);
    }
        
}