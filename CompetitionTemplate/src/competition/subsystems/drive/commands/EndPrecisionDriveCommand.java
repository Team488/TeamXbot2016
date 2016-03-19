package competition.subsystems.drive.commands;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class EndPrecisionDriveCommand extends BaseCommand{
    public DriveSubsystem drive;
    DoubleProperty drivePower;
    
    public EndPrecisionDriveCommand(DriveSubsystem drive, XPropertyManager propMan){
        this.drive = drive;
        drivePower = propMan.createPersistentProperty("drivePowerAfterPrecisionDrive", 1.0);
        
        this.requires(this.drive);
    }

    @Override
    public void initialize() {
        drive.tankDrive(drivePower.get(), drivePower.get());
    }

    @Override
    public void execute() {

    }

}
