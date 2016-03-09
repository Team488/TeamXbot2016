package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.XPropertyManager;


public class TankDriveWithJoysticksCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;
    final BooleanProperty enableSquaredTankInputs;
    
    @Inject
    public TankDriveWithJoysticksCommand(
            OperatorInterface oi, 
            DriveSubsystem driveSubsystem,
            XPropertyManager propMan)
    {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.requires(this.driveSubsystem);
        
        enableSquaredTankInputs = propMan.createPersistentProperty("EnableSquaredTankInputs", false);
    }
    
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        
        double left = oi.leftJoystick.getVector().y;
        double right = oi.rightJoystick.getVector().y;
        
        if (enableSquaredTankInputs.get()) {
            left = left * Math.abs(left);
            right = right * Math.abs(right);
        }
        
        driveSubsystem.tankDrive(left, right);
    }

}
