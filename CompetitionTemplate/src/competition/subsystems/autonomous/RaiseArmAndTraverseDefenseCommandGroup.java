package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmAngleMaintainerCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.arm.arm_commands.WaitForArmCalibrationCommand;
import competition.subsystems.drive.commands.CalibrateHeadingCommand;
import competition.subsystems.drive.commands.HeadingDriveCommand;
import competition.subsystems.drive.commands.TraverseDefenseCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class RaiseArmAndTraverseDefenseCommandGroup extends CommandGroup{
      
    TraverseDefenseCommand moveFirst;
    TraverseDefenseCommand traverse;
    SetArmToAngleCommand setArm;
    CalibrateHeadingCommand calibrateHeading;
    HeadingDriveCommand moveToClearDefense;
    
    DoubleProperty moveFirstDuration;
    DoubleProperty moveToClearDefenseDuration;
    
    public String label;
    
    @Inject
    public RaiseArmAndTraverseDefenseCommandGroup(
            TraverseDefenseCommand moveFirst,
            WaitForArmCalibrationCommand waitForArmCalibration,
            SetArmToAngleCommand setArm,
            TraverseDefenseCommand traverse,
            CalibrateHeadingCommand calibrateHeading,
            HeadingDriveCommand moveToClearDefense,
            XPropertyManager propMan) {
        
        this.traverse = traverse;
        this.setArm = setArm;
        this.calibrateHeading = calibrateHeading;
        this.moveFirst = moveFirst;
        this.moveToClearDefense = moveToClearDefense;
        
        moveFirstDuration = propMan.createPersistentProperty("MoveFirstDuration", 0.75);
        moveToClearDefenseDuration = propMan.createPersistentProperty("RaiseArmAndTraverse MoveToClearDuration", 0.5);
        
        this.addSequential(this.calibrateHeading);
        this.addSequential(moveFirst);
        this.addSequential(waitForArmCalibration);
        this.addSequential(this.setArm);
        this.addSequential(this.traverse);
        // TODO: have this timeout be in the command itself vs part of the sequencing. Then we could use it for move first as well
        this.addSequential(this.moveToClearDefense, this.moveToClearDefenseDuration.get());
    }
    
    public void setTraversalProperties(double power, double heading, double minSeconds, double maxSeconds) {
        traverse.setPower(power);
        traverse.setTargetHeading(heading);
        traverse.setTimeLimits(minSeconds, maxSeconds);
        
        moveFirst.setTargetHeading(heading);
        moveFirst.setTimeLimits(moveFirstDuration.get(), moveFirstDuration.get() + 0.05);
        
        moveToClearDefense.setTargetHeading(heading);
        
        // Have the moving power match the sign of the tarversal power but be generally lower
        double moveFirstPower = 0.4;
        if (power < 0) {
            moveFirstPower *= -1;
        }
        
        moveFirst.setPower(moveFirstPower);
        moveToClearDefense.setPower(moveFirstPower);
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
