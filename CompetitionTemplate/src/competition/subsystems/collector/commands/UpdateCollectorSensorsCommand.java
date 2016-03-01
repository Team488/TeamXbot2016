package competition.subsystems.collector.commands;

import com.google.inject.Inject;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.collector.CollectorSubsystem;
import competition.subsystems.hanger.HookSubsystem;
import xbot.common.command.BaseCommand;

public class UpdateCollectorSensorsCommand extends BaseCommand {
    CollectorSubsystem collectorSubsystem;
    
    @Inject
    public UpdateCollectorSensorsCommand(CollectorSubsystem collectorSubsystem) {
        this.collectorSubsystem = collectorSubsystem;
        this.setRunWhenDisabled(true);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        collectorSubsystem.isBallInCollector();
    }

}
