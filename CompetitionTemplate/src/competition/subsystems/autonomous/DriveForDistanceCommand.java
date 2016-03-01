package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class DriveForDistanceCommand extends BaseCommand{
    protected DriveSubsystem driveSubsystem; 
    protected PoseSubsystem poseSubsystem;
    
    DoubleProperty targetRange;
    
    PIDManager distanceDrivePid;
    
    public double targetDistance;
    double targetEncoderDistance;
    double currentDistance;
    
    @Inject
    public DriveForDistanceCommand(PoseSubsystem poseSubsystem, DriveSubsystem driveSubsystem, XPropertyManager propManager){
        this.driveSubsystem = driveSubsystem;
        this.poseSubsystem = poseSubsystem;
        
        distanceDrivePid = new PIDManager("DistanceDrivePID", propManager, 1, 0, 0);
        targetRange = propManager.createPersistentProperty("DistanceTargetRange", 3.0);
    }
    
    public void setTargetDistance(double distance) {
        targetDistance = distance;
    }

    @Override
    public void initialize() {
        poseSubsystem.resetDistanceTraveled();
    }

    @Override
    public void execute() {
        currentDistance = poseSubsystem.getRobotOrientedTotalDistanceTraveled().y;
        double power = distanceDrivePid.calculate(targetDistance, currentDistance);
        driveSubsystem.tankDrive(power, power);
    }
    
    public boolean isFinished(){
        currentDistance = poseSubsystem.getRobotOrientedTotalDistanceTraveled().y;
        return Math.abs(targetDistance - currentDistance) < targetRange.get(); 
    }
    
    public void end(){
        driveSubsystem.tankDrive(0, 0);
    }
    
}
