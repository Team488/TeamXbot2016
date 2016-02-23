package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;
import edu.wpi.first.wpilibj.Timer;
import xbot.common.properties.XPropertyManager;

public class TraverseDefenseCommand extends HeadingDriveCommand {
    
    double minTraversalTimeSec;
    double maxTraversalTimeSec;
    
    final Timer timer;
    
    double startTime;
    
    boolean wasEverOnDefense = false;
    
    @Inject
    public TraverseDefenseCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, XPropertyManager propMan,
            HeadingModule headingModule, Timer timer) {
        super(driveSubsystem, pose, propMan, headingModule);
        this.timer = timer;
    }
    
    public void setTimeLimits(double minTraversalTimeSec, double maxTraversalTimeSec) {
        this.minTraversalTimeSec = minTraversalTimeSec;
        this.maxTraversalTimeSec = maxTraversalTimeSec;
    }
    
    @Override 
    public void initialize() {
        super.initialize();
        startTime = this.timer.getFPGATimestamp();
        wasEverOnDefense = false;
    }
    
    @Override 
    public boolean isFinished() {
        boolean onDefense = this.pose.getDefenseState() == MonitorDefenseTraversalModule.DefenseState.OnDefense;
        wasEverOnDefense = wasEverOnDefense || onDefense;
        
        double timeElapsed = this.timer.getFPGATimestamp() - startTime;
        
        if(timeElapsed < minTraversalTimeSec) {
            // We want to keep trying at least for min time
            return false;
        } else if(timeElapsed < maxTraversalTimeSec) {
            // Between min and max, keep trying until we're off the defenses
            boolean offDefense = this.pose.getDefenseState() == MonitorDefenseTraversalModule.DefenseState.NotOnDefense;
            return wasEverOnDefense && offDefense;
        } else {
            // If it's been longer than max, give up and stop
            return true;
        }
    }

}
