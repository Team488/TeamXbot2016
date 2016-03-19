package competition.subsystems.drive.commands;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class EndPrecisionDriveCommand extends BaseCommand{
    public DriveSubsystem drive;
    
    public EndPrecisionDriveCommand(DriveSubsystem drive, XPropertyManager propMan){
        this.drive = drive;
    }

    @Override
    public void initialize() {
        drive.enablePrecisionDrive.set(false);
    }

    @Override
    public void execute() {

    }
    
    @Override
    public boolean isFinished() {
        return true;
    }

}
