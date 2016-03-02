package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmAngleMaintainerCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.drive.commands.TraverseDefenseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class RaiseArmAndTraverseDefenseCommandGroup extends CommandGroup{
      
    TraverseDefenseCommand traverse;
    SetArmToAngleCommand setArm;
    
    @Inject
    public RaiseArmAndTraverseDefenseCommandGroup(
            SetArmToAngleCommand setArm,
            TraverseDefenseCommand traverse,
            ArmAngleMaintainerCommand maintainArm) {
        
        this.traverse = traverse;
        this.setArm = setArm;
        
        this.addParallel(this.setArm);
        this.addParallel(maintainArm);
        this.addParallel(this.traverse);
    }
    
    public void setTraversalProperties(double power, double heading, double minSeconds, double maxSeconds) {
        traverse.setPower(power);
        traverse.setTarget(heading);
        traverse.setTimeLimits(minSeconds, maxSeconds);
    }
    
    public void setArmAngle(double goalAngle) {
        setArm.setGoalAngle(goalAngle);
    }

}
