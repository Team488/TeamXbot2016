package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmAngleMaintainerCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.arm.arm_commands.WaitForArmCalibrationCommand;
import competition.subsystems.drive.commands.CalibrateHeadingCommand;
import competition.subsystems.drive.commands.TraverseDefenseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RaiseArmAndTraverseDefenseCommandGroup extends CommandGroup{
      
    TraverseDefenseCommand traverse;
    SetArmToAngleCommand setArm;
    CalibrateHeadingCommand calibrateHeading;
    
    public String label;
    
    @Inject
    public RaiseArmAndTraverseDefenseCommandGroup(
            WaitForArmCalibrationCommand waitForArmCalibration,
            SetArmToAngleCommand setArm,
            TraverseDefenseCommand traverse,
            CalibrateHeadingCommand calibrateHeading) {
        
        this.traverse = traverse;
        this.setArm = setArm;
        this.calibrateHeading = calibrateHeading;
        
        this.addSequential(waitForArmCalibration);
        this.addSequential(this.calibrateHeading);
        this.addSequential(this.setArm);
        this.addSequential(this.traverse);
    }
    
    public void setTraversalProperties(double power, double heading, double minSeconds, double maxSeconds) {
        traverse.setPower(power);
        traverse.setTarget(heading);
        traverse.setTimeLimits(minSeconds, maxSeconds);
    }
    
    public void setArmAngle(double goalAngle) {
        setArm.setGoalAngle(goalAngle);
    }
    
    public void setInitialHeading(double initialHeading) {
        calibrateHeading.setHeading(initialHeading);
    }

    @Override
    public String toString() {
        if(label != null){
            return label;
        }
        else {
            return super.toString();
        }
    }
}
