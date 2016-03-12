package competition.subsystems.lighting;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.lighting.LightingSubsystem.LightingState;
import xbot.common.command.BaseCommand;

public class UpdateLightingStateCommand extends BaseCommand {
    
    final LightingSubsystem lightingSubsystem;
    final CollectorSubsystem collectorSubsystem;
    
    @Inject
    public UpdateLightingStateCommand(LightingSubsystem lightingSubsystem, CollectorSubsystem collectorSubsystem) {
        this.lightingSubsystem = lightingSubsystem;
        this.collectorSubsystem = collectorSubsystem;
        this.setRunWhenDisabled(true);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(!lightingSubsystem.isRobotEnabled()) {
            this.lightingSubsystem.setLightingState(LightingState.Disabled);
        }
        else if(collectorSubsystem.isBallInCollector()) {
            this.lightingSubsystem.setLightingState(LightingState.BallCaptured);
        } else {
            this.lightingSubsystem.setLightingState(LightingState.Enabled);
        }
    }

}
