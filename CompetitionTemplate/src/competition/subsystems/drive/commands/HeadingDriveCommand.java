package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.ContiguousDouble;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;


public class HeadingDriveCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final PoseSubsystem pose;
    
    private ContiguousHeading targetHeading;
    private double targetPower;
    private HeadingModule headingModule;
    
    private DoubleProperty forwardPower;
    
    @Inject
    public HeadingDriveCommand(DriveSubsystem driveSubsystem, 
                               PoseSubsystem pose,
                               PropertyManager propMan,
                               HeadingModule headingModule)
    {
        this.driveSubsystem = driveSubsystem;
        this.pose = pose;
        this.headingModule = headingModule; 
        
        targetHeading = new ContiguousHeading(PoseSubsystem.FACING_AWAY_FROM_DRIVERS);
        forwardPower = propMan.createEphemeralProperty("ForwardPower", 0.0);
        
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
        headingModule.reset();
    }

    @Override
    public void execute() {
        double rotationalPower = headingModule.calculateHeadingPower(targetHeading.getValue());
        
        double leftPower = forwardPower.get() - rotationalPower;
        double rightPower = forwardPower.get() + rotationalPower;
        
        driveSubsystem.tankDriveSafely(leftPower, rightPower);
    }

}
