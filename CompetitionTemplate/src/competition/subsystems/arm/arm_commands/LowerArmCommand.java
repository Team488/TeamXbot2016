package competition.subsystems.arm.arm_commands;
import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class LowerArmCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    DoubleProperty lowerArmPower;

    @Inject
    public LowerArmCommand(ArmSubsystem armSubsystem, XPropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        lowerArmPower = propManager.createPersistentProperty("ArmLoweringPower", -1.0);
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        armSubsystem.setArmMotorPower(lowerArmPower.get());
    }
    
    @Override
    public boolean isFinished() {
        return armSubsystem.isArmAtMaxAngleHeight();
    }
    
    @Override
    public void end() {
        armSubsystem.setArmMotorPower(0);
    }
        
}