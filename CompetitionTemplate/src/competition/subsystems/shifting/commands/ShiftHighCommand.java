package competition.subsystems.shifting.commands;

import competition.subsystems.shifting.ShiftingSubsystem;
import xbot.common.command.BaseCommand;

public class ShiftHighCommand extends BaseCommand{
    ShiftingSubsystem shiftingSubsystem;
    
    public ShiftHighCommand (ShiftingSubsystem shiftingSubsystem) {
        this.shiftingSubsystem = shiftingSubsystem;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void initialize() {
        shiftingSubsystem.shiftHigh();
    }
    

}
