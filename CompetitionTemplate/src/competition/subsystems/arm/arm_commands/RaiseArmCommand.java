package competition.subsystems.arm.arm_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RaiseArmCommand extends BaseCommand {
    ArmSubsystem armSubsystem;
    DoubleProperty raiseArmPower;

    @Inject
    public RaiseArmCommand(ArmSubsystem armSubsystem, XPropertyManager propManager) {
        this.armSubsystem = armSubsystem;
        raiseArmPower = propManager.createPersistentProperty("ArmRaisingPower", 1.0);
        this.requires(this.armSubsystem); 
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        armSubsystem.setArmMotorPower(raiseArmPower.get());
    }
    
    @Override
    public boolean isFinished() {
        return armSubsystem.isArmAtMaximumHeight();
    }
    
    @Override
    public void end() {
        armSubsystem.setArmMotorPower(0);
    }
        
}
