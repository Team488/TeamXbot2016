package competition.subsystems.portcullisWheels.commands;


import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.portcullisWheels.PortcullisWheelsSubsystem;

public class SpinPortcullisWheelsCommand extends BaseCommand {

    final PortcullisWheelsSubsystem portcullisWheels;
    private DoubleProperty portcullisWheelsPower;
    private PortcullisDirection direction;
    
    @Inject
    public SpinPortcullisWheelsCommand(PortcullisWheelsSubsystem portcullisWheels, XPropertyManager propMan)
    {
        this.portcullisWheels = portcullisWheels;
        portcullisWheelsPower = propMan.createPersistentProperty("PortcullisWheelsPower", 1.0);
        this.requires(portcullisWheels);
    }
    
    public enum PortcullisDirection {
        Stop,
        Up,
        Down
    }
    
    public void setDirection(PortcullisDirection direction) {
        this.direction = direction;
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        switch (direction) {
            case Stop:
                portcullisWheels.setWheelSpeed(0);
                break;
            case Up:
                portcullisWheels.setWheelSpeed(portcullisWheelsPower.get());
                break;
            case Down:
                portcullisWheels.setWheelSpeed(-portcullisWheelsPower.get());
                break;
            default:
                portcullisWheels.setWheelSpeed(0);
                break;
        }
    }

}
