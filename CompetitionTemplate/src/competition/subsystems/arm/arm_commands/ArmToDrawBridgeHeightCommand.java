package competition.subsystems.arm.arm_commands;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.arm.ArmTargetSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class ArmToDrawBridgeHeightCommand extends SetArmToAngleCommand {
    DoubleProperty position;

    @Inject
    public ArmToDrawBridgeHeightCommand(ArmTargetSubsystem armTargetSubsystem, XPropertyManager propManager) {
        super(armTargetSubsystem);
        position = propManager.createPersistentProperty("ArmHeight DrawBridge", 50.0);
        
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
