package competition.subsystems.autonomous;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmAngleMaintainerCommand;
import competition.subsystems.arm.arm_commands.DropKickstandCommand;
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
    DropKickstandCommand dropKickstand;
    
    DoubleProperty moveFirstDuration;
    
    public String label;
    
    @Inject
    public RaiseArmAndTraverseDefenseCommandGroup(
            DropKickstandCommand dropKickstand,
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
        this.dropKickstand = dropKickstand;
        
        moveFirstDuration = propMan.createPersistentProperty("MoveFirstDuration", 0.75);
        
        this.addSequential(this.calibrateHeading);
        this.addSequential(dropKickstand);
        this.addSequential(moveFirst);
        this.addSequential(waitForArmCalibration);
        this.addSequential(this.setArm);
        this.addSequential(this.traverse);
    }
    
    public void setTraversalProperties(double power, double heading, double minSeconds, double maxSeconds) {
        traverse.setPower(power);
        traverse.setTarget(heading);
        traverse.setTimeLimits(minSeconds, maxSeconds);
        
        moveFirst.setTarget(heading);
        moveFirst.setTimeLimits(moveFirstDuration.get(), moveFirstDuration.get() + 0.05);
        
        double moveFirstPower = 0.4;
        if (power < 0) {
            moveFirstPower *= -1;
        }
        
        moveFirst.setPower(moveFirstPower);
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
