package competition.subsystems.arm.arm_commands;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class ArmToScalingHeightCommand extends SetArmToAngleCommand {
    DoubleProperty position;

    @Inject
    public ArmToScalingHeightCommand(ArmTargetSubsystem armTargetSubsystem, XPropertyManager propManager) {
        super(armTargetSubsystem);
        position = propManager.createPersistentProperty("ArmHeight Scaling", 35.0);
        
    }

    @Override
    public void initialize() {
        this.setGoalAngle(position.get());
        super.initialize();
    }

    @Override
    public void execute() {

    }
    
    @Override
    public boolean isFinished() {
        return this.armTarget.isWithinRange();
    }   
}
