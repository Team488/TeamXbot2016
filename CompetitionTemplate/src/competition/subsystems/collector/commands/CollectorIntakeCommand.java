package competition.subsystems.collector.commands;

import com.google.inject.Inject;

import competition.subsystems.collector.CollectorSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;

public class CollectorIntakeCommand extends BaseCommand{
    CollectorSubsystem collectorSubsystem;
    DoubleProperty intakePower;
    
    @Inject
    public CollectorIntakeCommand(CollectorSubsystem collectorSubsystem, PropertyManager propManager) {
        this.collectorSubsystem = collectorSubsystem;
        intakePower = propManager.createPersistentProperty("CollectorIntakePower", 1.0);
    }
    
    @Override
    public void initialize() {
        collectorSubsystem.setIntakePower(intakePower.get()); 
    }

    public void end() {
        collectorSubsystem.setIntakePower(0);
    }

    @Override
    public void execute() {
      
    }


}
