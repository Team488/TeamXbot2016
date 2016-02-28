package competition.subsystems.collector.commands;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class CollectorEjectCommand extends BaseCommand{
    CollectorSubsystem collectorSubsystem;

    @Inject
    public CollectorEjectCommand(CollectorSubsystem collectorSubsystem, XPropertyManager propManager) {
        this.collectorSubsystem = collectorSubsystem;
    }
    
    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        collectorSubsystem.eject();
    }

    public void end() {
        collectorSubsystem.stopCollector();
    }

}
