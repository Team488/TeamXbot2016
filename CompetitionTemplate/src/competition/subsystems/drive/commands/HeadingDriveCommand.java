package competition.subsystems.drive.commands;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.ContiguousDouble;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;


public class HeadingDriveCommand extends BaseCommand {

    private static Logger log = Logger.getLogger(HeadingDriveCommand.class);
    
    final DriveSubsystem driveSubsystem;
    final PoseSubsystem pose;
    
    private ContiguousHeading targetHeading;
    private double targetPower;
    private HeadingModule headingModule;
    
    @Inject
    public HeadingDriveCommand(DriveSubsystem driveSubsystem, 
                               PoseSubsystem pose,
                               XPropertyManager propMan,
                               HeadingModule headingModule)
    {
        this.driveSubsystem = driveSubsystem;
        this.pose = pose;
        this.headingModule = headingModule; 
        
        targetHeading = new ContiguousHeading(PoseSubsystem.FACING_AWAY_FROM_DRIVERS);
        
        this.requires(this.driveSubsystem);
    }
    
    public void setTarget(double heading) {
        targetHeading.setValue(heading);
    }
    
    public void setPower(double power) {
        targetPower = power;
    }
    
    @Override
    public void initialize() {
        log.info("initializing HeadingDriveCommand with power " + targetPower + " and heading " + targetHeading);
        headingModule.reset();
    }

    @Override
    public void execute() {
        double rotationalPower = headingModule.calculateHeadingPower(targetHeading.getValue());
        
        double leftPower = targetPower - rotationalPower;
        double rightPower = targetPower + rotationalPower;
        
        driveSubsystem.tankDriveSafely(leftPower, rightPower);
    }

}
