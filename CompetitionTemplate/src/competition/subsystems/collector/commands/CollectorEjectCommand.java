package competition.subsystems.collector.commands;

import competition.subsystems.collector.CollectorSubsystem;
import competition.operator_interface.OperatorInterface;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;

public class CollectorEjectCommand extends BaseCommand{
    CollectorSubsystem collectorSubsystem;
    OperatorInterface operatorInterface;
    DoubleProperty intakePower;

    public CollectorEjectCommand(CollectorSubsystem collectorSubsystem, OperatorInterface oi, PropertyManager propManager) {
        this.collectorSubsystem = collectorSubsystem;
        operatorInterface = oi;
        intakePower = propManager.createPersistentProperty("CollectorIntakePower", -1.0);
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
