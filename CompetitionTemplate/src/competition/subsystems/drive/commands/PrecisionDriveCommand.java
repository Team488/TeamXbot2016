package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class PrecisionDriveCommand extends BaseCommand{
    public DriveSubsystem drive;
    
    @Inject
    public PrecisionDriveCommand(DriveSubsystem drive){
        this.drive = drive;
    }

    @Override
    public void initialize() {
        this.drive.enablePrecisionDrive.set(true);
    }

    @Override
    public void execute() {
        
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
