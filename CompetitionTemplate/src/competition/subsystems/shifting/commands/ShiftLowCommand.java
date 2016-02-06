package competition.subsystems.shifting.commands;

import competition.subsystems.shifting.ShiftingSubsystem;
import xbot.common.command.BaseCommand;

public class ShiftLowCommand extends BaseCommand{
    ShiftingSubsystem shiftingSubsystem;
    
    public ShiftLowCommand(ShiftingSubsystem shiftingSubsystem){
        this.shiftingSubsystem = shiftingSubsystem;
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void initialize() {
        shiftingSubsystem.shiftLow();
    }


}
