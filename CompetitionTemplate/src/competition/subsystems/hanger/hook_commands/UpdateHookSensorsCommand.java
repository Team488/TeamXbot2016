package competition.subsystems.hanger.hook_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.hanger.HookPoseSubsystem;
import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;

public class UpdateHookSensorsCommand extends BaseCommand {
    HookPoseSubsystem hookPose;
    
    @Inject
    public UpdateHookSensorsCommand(HookPoseSubsystem hookPose) {
        this.hookPose = hookPose;
        this.setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        hookPose.updateHookSensors();
    }

}
