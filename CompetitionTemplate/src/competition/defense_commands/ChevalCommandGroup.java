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

    DriveToDistanceCommand backUp;
    ArmToBottomCommand dropArm;
    DriveToDistanceCommand crossDefense;
    
    DoubleProperty chevalBackUpDistance;
    DoubleProperty chevalCrossDefenseDistance;
    DoubleProperty chevalWaitTime;
    
    @Inject
    public ChevalCommandGroup(
            DriveToDistanceCommand backUp,
            ArmToBottomCommand dropArm,
            DriveToDistanceCommand crossDefense,
            XPropertyManager propMan) {
        
        this.backUp = backUp;
        this.dropArm = dropArm;
        this.crossDefense = crossDefense;
        
        chevalBackUpDistance = propMan.createPersistentProperty("ChevalBackUpDistance", -6.0);
        chevalCrossDefenseDistance = propMan.createPersistentProperty("ChevalCrossDefenseDistance", 20.0);
        chevalWaitTime = propMan.createPersistentProperty("ChevalWaitTime", 0.5);
        
        backUp.setTargetDistance(chevalBackUpDistance.get());
        crossDefense.setTargetDistance(chevalCrossDefenseDistance.get());
        WaitCommand wait = new WaitCommand(chevalWaitTime.get());
        
        this.addSequential(backUp);
        this.addSequential(dropArm);
        this.addSequential(wait);
        this.addSequential(crossDefense);
    }
}
