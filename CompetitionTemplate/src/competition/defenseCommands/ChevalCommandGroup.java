package competition.defenseCommands;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class ChevalCommandGroup extends CommandGroup{

    @Inject
    public ChevalCommandGroup(
            DriveToDistanceCommand backUp,
            ArmToBottomCommand dropArm,
            DriveToDistanceCommand crossDefense) {
        
        backUp.setTargetDistance(-6);
        crossDefense.setTargetDistance(20);
        
        this.addSequential(backUp);
        this.addSequential(dropArm);
        this.addSequential(crossDefense);
    }
}
