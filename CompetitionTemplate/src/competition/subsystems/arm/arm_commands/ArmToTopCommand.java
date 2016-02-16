package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class ArmToTopCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    ArmTargetSubsystem armTargetSubsystem;
    DoubleProperty top;

    @Inject
    public ArmToTopCommand(ArmSubsystem armSubsystem, ArmTargetSubsystem armTargetSubsystem, XPropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        top = propManager.createPersistentProperty("ArmLargestAngle", 90.0);
        this.armTargetSubsystem = armTargetSubsystem;
        
        this.requires(this.armSubsystem);
    }

    @Override
    public void initialize() {
        armTargetSubsystem.setTargetAngle(top.get());
    }

    @Override
    public void execute() {
        
    }
    
    @Override
    public boolean isFinished() {
        return armTargetSubsystem.isFinished();
    }
    
    @Override
    public void end() {
        armSubsystem.setArmMotorPower(0);
    }
        
}
