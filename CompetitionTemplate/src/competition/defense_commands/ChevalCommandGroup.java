package competition.defense_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class ChevalCommandGroup extends CommandGroup{
    
    DoubleProperty chevalBackUpDistance;
    DoubleProperty chevalBeginCrossDefenseDistance;
    DoubleProperty chevalFinishCrossDefenseDistance;
    DoubleProperty chevalWaitTime;
    DoubleProperty chevalSafeArmHeight;
    
    @Inject
    public ChevalCommandGroup(
            DriveToDistanceCommand backUp,
            ArmToBottomCommand dropArm,
            DriveToDistanceCommand beginCrossDefense,
            SetArmToAngleCommand raiseArmSafely,
            DriveToDistanceCommand finishCrossDefense,
            XPropertyManager propMan) {
        
        
        chevalBackUpDistance = propMan.createPersistentProperty("ChevalBackUpDistance", -2.0);
        chevalBeginCrossDefenseDistance = propMan.createPersistentProperty("ChevalBeginCrossDefenseDistance", 10.0);
        chevalFinishCrossDefenseDistance = propMan.createPersistentProperty("ChevalFinishCrossDefenseDistance", 30.0);
        chevalWaitTime = propMan.createPersistentProperty("ChevalWaitTime", 0.5);
        chevalSafeArmHeight = propMan.createPersistentProperty("ChevalSafeArmHeight", 25.0);
        
        backUp.setTargetDistance(chevalBackUpDistance.get());
        beginCrossDefense.setTargetDistance(chevalBeginCrossDefenseDistance.get());
        finishCrossDefense.setTargetDistance(chevalFinishCrossDefenseDistance.get());
        raiseArmSafely.setGoalAngle(chevalSafeArmHeight.get());
        WaitCommand wait = new WaitCommand(chevalWaitTime.get());
        
        this.addParallel(backUp);
        this.addSequential(dropArm);
        this.addSequential(wait);
        this.addSequential(beginCrossDefense);
        this.addParallel(raiseArmSafely);
        this.addParallel(finishCrossDefense);
    }
}
