package competition.subsystems.arm.arm_commands;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class ArmToBottomCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    ArmTargetSubsystem armTargetSubsystem;
    DoubleProperty bottom;

    @Inject
    public ArmToBottomCommand(ArmSubsystem armSubsystem, ArmTargetSubsystem armTargetSubsystem, XPropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        this.armTargetSubsystem = armTargetSubsystem;
        bottom = propManager.createPersistentProperty("ArmLowestAngle", 0.0);
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        armTargetSubsystem.setTargetAngle(bottom.get());
    }

    @Override
    public void execute() {

    }
    
    @Override
    public boolean isFinished() {
        return armTargetSubsystem.isWithinRange();
    }
    
    @Override
    public void end() {
        armSubsystem.setArmMotorPower(0);
    }
        
}
