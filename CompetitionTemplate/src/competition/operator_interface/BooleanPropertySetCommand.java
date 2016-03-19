package competition.operator_interface;

import xbot.common.command.BaseCommand;
import xbot.common.properties.BooleanProperty;

public class BooleanPropertySetCommand extends BaseCommand {
    
    final BooleanProperty property;
    final boolean toValue;
    
    public BooleanPropertySetCommand(BooleanProperty property, boolean toValue) {
        this.property = property;
        this.toValue = toValue;
        this.setRunWhenDisabled(true);
    }

    @Override
    public String toString() {
        return "BooleanPropertySetCommand: " + this.property.key + " toValue: " + this.toValue;
    }
    
    @Override
    public void initialize() {
        this.property.set(toValue);
    }

    @Override
    public void execute() {        
    }
    
    @Override
    public void end() {
        super.end();
        this.property.set(!toValue);
    }
}
