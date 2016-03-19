package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class PrecisionDriveCommand extends BaseCommand{
    public DriveSubsystem drive;
    DoubleProperty drivePower;
    DoubleProperty precisionFactor;
    Double precisionPower;
    
    @Inject
    public PrecisionDriveCommand(DriveSubsystem drive, XPropertyManager propManager){
        this.drive = drive;
        drivePower = propManager.createPersistentProperty("drivePowerBeforePrecisionDrive", 1.0);
        precisionFactor = propManager.createPersistentProperty("precisionFactor", 0.5);
        
        this.requires(this.drive);
    }

    @Override
    public void initialize() {
        precisionPower = drivePower.get() * precisionFactor.get();
        drive.tankDrive(precisionPower, precisionPower);
    }

    @Override
    public void execute() {
        
    }

}
