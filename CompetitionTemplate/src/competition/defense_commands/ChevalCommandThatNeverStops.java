package competition.defense_commands;

import com.google.inject.Inject;

import competition.subsystems.arm.arm_commands.ArmToBottomCommand;
import competition.subsystems.drive.commands.DriveToDistanceCommand;
import xbot.common.properties.XPropertyManager;

public class ChevalCommandThatNeverStops extends ChevalCommandGroup {

    @Inject
    public ChevalCommandThatNeverStops(DriveToDistanceCommand backUp, ArmToBottomCommand dropArm,
            DriveToDistanceCommand crossDefense, XPropertyManager propMan) {
        super(backUp, dropArm, crossDefense, propMan);
    }
    
    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }

}
