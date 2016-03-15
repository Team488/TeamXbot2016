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
    public DoubleProperty autoStopOverrideTime;

    @Inject
    public CollectorIntakeCommand(CollectorSubsystem collectorSubsystem, XPropertyManager propManager) {
        this.collectorSubsystem = collectorSubsystem;
        
        enableCollectorAutoStop = propManager.createPersistentProperty("Enable Collector Auto Stop", false);
        autoStopOverrideTime = propManager.createPersistentProperty("Intake auto-stop delay", 0.3);
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
            if (this.timeSinceInitialized() < autoStopOverrideTime.get() || !collectorSubsystem.isBallInCollector()) {
                collectorSubsystem.intake();
            }
            else {
                collectorSubsystem.stopCollector();
            }
        }
        else {
            collectorSubsystem.intake();
        }
    }


}
