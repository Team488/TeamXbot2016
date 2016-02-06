package competition.subsystems.arm.arm_commands;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;

public class RaiseArmCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    DoubleProperty raiseArmPower;

    public RaiseArmCommand(ArmSubsystem armSubsystem, PropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        raiseArmPower = propManager.createPersistentProperty("ArmRaisingPower", 1.0);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        armSubsystem.armMotorPower(raiseArmPower.get());
    }
    
    @Override
    public boolean isFinished() {
        return armSubsystem.isArmAtMaximumHeight();
    }
    
    @Override
    public void end() {
        armSubsystem.armMotorPower(0);
    }
        
}
