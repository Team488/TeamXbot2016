package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import xbot.common.math.ContiguousDouble;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.PIDManager;
import xbot.common.properties.PropertyManager;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.PoseSubsystem;

public class HeadingModule {

    final PoseSubsystem pose;
    private PIDManager headingDrivePid;
    
    private ContiguousHeading targetHeading;
    
    @Inject
    public HeadingModule(PoseSubsystem pose, PropertyManager propMan)
    {
        this.pose = pose;
        
        headingDrivePid = new PIDManager("HeadingModule", propMan, 1, 0, 0);
        targetHeading = new ContiguousHeading();
    }
    
    public void reset() {
        headingDrivePid.reset();
    }
    
    public double calculateHeadingPower(double desiredHeading) {
        // We need to calculate our own error function. Why?
        // PID works great, but it assumes there is a linear relationship between your current state and
        // your target state. Since rotation is circular, that's not the case: if you are at 170 degrees,
        // and you want to go to -170 degrees, you could travel -340 degrees... or just +20. 
        
        // So, we perform our own error calculation here that takes that into account (thanks to the ContiguousDouble
        // class, which is aware of such circular effects), and then feed that into a PID where
        // Goal is 0 and Current is our error.
        
        // This will have a side-effect of reversing the power output of the system, so we need to remember
        // at the very end to reverse it one more time to cancel that out.
        targetHeading.setValue(desiredHeading);
        double errorInDegrees = pose.getCurrentHeading().difference(targetHeading);
        
        // Let's normalize the error into a -1 to 1 range. Convenient for further math.
        double normalizedError = errorInDegrees / 180;
        
        // Now we feed it into a PID system, where the goal is to have 0 error.
        double rotationalPower = headingDrivePid.calculate(0, normalizedError);
        
        // reverse the power (see above)
        rotationalPower *= -1;
        
        return rotationalPower;        
    }
}
