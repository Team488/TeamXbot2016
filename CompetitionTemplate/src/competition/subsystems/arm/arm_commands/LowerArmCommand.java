package competition.subsystems.arm.arm_commands;
import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;

public class LowerArmCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    DoubleProperty lowerArmPower;

    @Inject
    public LowerArmCommand(ArmSubsystem armSubsystem, PropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        lowerArmPower = propManager.createPersistentProperty("ArmLoweringPower", -1.0);
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        armSubsystem.armMotorPower(lowerArmPower.get());
    }
    
    @Override
    public boolean isFinished() {
        return armSubsystem.isArmAtMinimumHeight();
    }
    
    @Override
    public void end() {
        armSubsystem.armMotorPower(0);
    }
        
}