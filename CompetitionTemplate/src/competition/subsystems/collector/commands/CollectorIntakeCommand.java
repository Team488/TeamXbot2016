package competition.subsystems.collector.commands;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class CollectorIntakeCommand extends BaseCommand{
    CollectorSubsystem collectorSubsystem;
    public BooleanProperty enableCollectorAutoStop;

    @Inject
    public CollectorIntakeCommand(CollectorSubsystem collectorSubsystem, XPropertyManager propManager) {
        this.collectorSubsystem = collectorSubsystem;
        enableCollectorAutoStop = propManager.createPersistentProperty("Enable Collector Auto Stop", false);
    }
    
    @Override
    public void initialize() { 
    }

    public void end() {
        collectorSubsystem.stopCollector();
    }

    @Override
    public void execute() {
        if (enableCollectorAutoStop.get()) {
            if (!collectorSubsystem.isBallInCollector()) {
                collectorSubsystem.startIntake();
            }
            else {
                collectorSubsystem.stopCollector();
            }
        }
        else {
            collectorSubsystem.startIntake();
        }
    }


}
