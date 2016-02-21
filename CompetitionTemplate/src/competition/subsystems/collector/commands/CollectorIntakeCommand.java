package competition.subsystems.collector.commands;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class CollectorIntakeCommand extends BaseCommand{
    CollectorSubsystem collectorSubsystem;
    DoubleProperty intakePower;
    public BooleanProperty enableCollectorAutoStop;

    @Inject
    public CollectorIntakeCommand(CollectorSubsystem collectorSubsystem, XPropertyManager propManager) {
        this.collectorSubsystem = collectorSubsystem;
        intakePower = propManager.createPersistentProperty("CollectorIntakePower", 1.0);
        enableCollectorAutoStop = propManager.createPersistentProperty("Enable Collector Auto Stop", false);
    }
    
    @Override
    public void initialize() { 
    }

    public void end() {
        collectorSubsystem.setIntakePower(0);
    }

    @Override
    public void execute() {
        if (enableCollectorAutoStop.get()) {
            if (!collectorSubsystem.isBallInCollector()) {
                collectorSubsystem.setIntakePower(intakePower.get());
            }
            else {
                collectorSubsystem.setIntakePower(0);
            }
        }
        else {
            collectorSubsystem.setIntakePower(intakePower.get());
        }
    }


}
