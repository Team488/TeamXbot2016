package competition.defense_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.arm.arm_commands.SetArmToAngleCommand;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import xbot.common.properties.XPropertyManager;

public class ChevalCommandThatNeverStops extends ChevalCommandGroup {
    
    @Inject
    public ChevalCommandThatNeverStops(DriveToDistanceCommand backUp, ArmToBottomCommand dropArm,
            DriveToDistanceCommand beginCrossDefense, SetArmToAngleCommand raiseArmSafely,
            DriveToDistanceCommand finishCrossDefense, XPropertyManager propMan) {
        super(backUp, dropArm, beginCrossDefense, raiseArmSafely, finishCrossDefense, propMan);
        // TODO Auto-generated constructor stub
    }    
    
    @Override
    protected boolean isFinished() {
        return false;
    }

}
