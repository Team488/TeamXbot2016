package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmAngleMaintainerCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.arm.arm_commands.WaitForArmCalibrationCommand;
import competition.subsystems.drive.commands.CalibrateHeadingCommand;
import competition.subsystems.drive.commands.TraverseDefenseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RaiseArmAndTraverseDefenseCommandGroup extends CommandGroup{
      
    TraverseDefenseCommand moveFirst;
    TraverseDefenseCommand traverse;
    SetArmToAngleCommand setArm;
    CalibrateHeadingCommand calibrateHeading;
    
    final DoubleProperty moveFirstDuration;
    final DoubleProperty moveFirstPower;
    
    public String label;
    
    @Inject
    public RaiseArmAndTraverseDefenseCommandGroup(
            TraverseDefenseCommand moveFirst,
            WaitForArmCalibrationCommand waitForArmCalibration,
            SetArmToAngleCommand setArm,
            TraverseDefenseCommand traverse,
            CalibrateHeadingCommand calibrateHeading,
            XPropertyManager propMan) {
        
        this.traverse = traverse;
        this.setArm = setArm;
        this.calibrateHeading = calibrateHeading;
        this.moveFirst = moveFirst;
        
        moveFirstDuration = propMan.createPersistentProperty("MoveFirstDuration", 0.75);
        moveFirstPower = propMan.createPersistentProperty("MoveFirstPower", 0.4);
        
        this.addSequential(this.calibrateHeading);
        this.addSequential(moveFirst);
        this.addSequential(waitForArmCalibration);
        this.addSequential(this.setArm);
        this.addSequential(this.traverse);
    }
    
    public void setTraversalProperties(double power, double heading, double minSeconds, double maxSeconds) {
        traverse.setPower(power);
        traverse.setTargetHeading(heading);
        traverse.setTimeLimits(minSeconds, maxSeconds);
        
        moveFirst.setTargetHeading(heading);
        moveFirst.setTimeLimits(moveFirstDuration.get(), moveFirstDuration.get() + 0.05);
        
        double finalMoveFirstPower = this.moveFirstPower.get();
        // Match sign
        if (power < 0) {
            finalMoveFirstPower *= -1;
        }
        
        moveFirst.setPower(finalMoveFirstPower);
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
