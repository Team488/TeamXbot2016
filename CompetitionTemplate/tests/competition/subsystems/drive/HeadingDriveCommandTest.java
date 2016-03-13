package competition.subsystems.drive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.drive.commands.HeadingDriveCommand;
import xbot.common.injection.BaseWPITest;

public class HeadingDriveCommandTest extends DriveSubsystemTest {

    HeadingDriveCommand command;
    
    @Before
    public void setup() {
        super.setup();
        this.command = this.injector.getInstance(HeadingDriveCommand.class);
    }
    
    @Test
    public void checkStraight() {
        command.setTargetHeading(PoseSubsystem.FACING_AWAY_FROM_DRIVERS);
        command.setPower(0.5);
        
        command.initialize();
        command.execute();
        checkChassisPower(0.5, 0.5);
    }
    
    @Test
    public void checkRight() {
        command.setTargetHeading(PoseSubsystem.FACING_AWAY_FROM_DRIVERS - 90);
        command.setPower(0.0);
        
        command.initialize();
        command.execute();
        
        verifyTurningRight();
    }
    
    @Test
    public void checkLeft() {
        command.setTargetHeading(PoseSubsystem.FACING_AWAY_FROM_DRIVERS + 90);
        command.setPower(0.0);
        
        command.initialize();
        command.execute();
        
        verifyTurningLeft();
    }
}