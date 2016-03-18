package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;

public class DriveWithGamePadCommand extends BaseCommand{
    DriveSubsystem driveSubsystem;
    OperatorInterface oi;
    
    @Inject
    public DriveWithGamePadCommand (DriveSubsystem driveSubsystem, OperatorInterface oi){
        this.driveSubsystem = driveSubsystem;
        this.oi = oi;
        this.requires(this.driveSubsystem);
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        //driveSubsystem.tankDrive(oi.driverGamePad.getRawAxis(1) * -1, oi.driverGamePad.getRawAxis(5) * -1);
    }
    
}
