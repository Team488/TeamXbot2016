package competition.subsystems.shifting.commands;

import com.google.inject.Inject;

import competition.subsystems.shifting.ShiftingSubsystem;
import xbot.common.command.BaseCommand;

public class ShiftHighCommand extends BaseCommand{
    ShiftingSubsystem shiftingSubsystem;
    
    @Inject
    public ShiftHighCommand (ShiftingSubsystem shiftingSubsystem) {
        this.requires(shiftingSubsystem);
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
