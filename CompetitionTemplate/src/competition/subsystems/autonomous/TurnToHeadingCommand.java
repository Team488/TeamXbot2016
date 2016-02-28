package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import competition.subsystems.drive.commands.HeadingModule;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class TurnToHeadingCommand extends BaseCommand{
    DriveSubsystem driveSubsystem;
    PoseSubsystem poseSubsystem;
    HeadingModule headingModule;
    double leftPower;
    double rightPower;
    
    DoubleProperty headingTargetRange;
    
    double targetHeading;
    
    @Inject
    public TurnToHeadingCommand(DriveSubsystem driveSubsystem, HeadingModule headingModule, PoseSubsystem poseSubsystem, XPropertyManager propManager){
        this.driveSubsystem = driveSubsystem;
        this.headingModule = headingModule;
        this.poseSubsystem = poseSubsystem;

        headingTargetRange = propManager.createPersistentProperty("headingTargetRange", 3.0);
    }
    
    public void turnRight(boolean right){
        double power = headingModule.calculateHeadingPower(targetHeading);
        if (right) {
            rightPower = power * -1;
            leftPower = power;
        } else {
            rightPower = power;
            leftPower = power * -1;
        }
    }
    
    public void setTargetHeading(double heading){
        targetHeading = heading;
    }

    @Override
    public void initialize(){
        headingModule.reset();
    }

    @Override
    public void execute(){
        driveSubsystem.tankDrive(leftPower, rightPower);
    }
    
    public boolean isFinished(){
        double errorInDegrees = poseSubsystem.getCurrentHeading().difference(targetHeading);
        return Math.abs(errorInDegrees) < headingTargetRange.get();
    }
    
    public void end(){
        driveSubsystem.tankDrive(0, 0);
    }

}
