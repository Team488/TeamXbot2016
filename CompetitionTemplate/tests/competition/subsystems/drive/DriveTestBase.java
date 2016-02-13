package competition.subsystems.drive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import xbot.common.injection.BaseWPITest;

public class DriveTestBase extends BaseWPITest {
    
    protected DriveSubsystem driveSubsystem;
    protected PoseSubsystem pose;
    
    @Before
    public void setup() {
        this.driveSubsystem = this.injector.getInstance(DriveSubsystem.class);
        this.pose = this.injector.getInstance(PoseSubsystem.class);
    }
    
    protected void checkChassisPower(double expectedLeftPower, double expectedRightPower) {
        assertEquals(expectedLeftPower, driveSubsystem.leftFrontDrive.get(), 0.001);
        assertEquals(expectedLeftPower, driveSubsystem.leftRearDrive.get(), 0.001);
        assertEquals(expectedRightPower, driveSubsystem.rightFrontDrive.get(), 0.001);
        assertEquals(expectedRightPower, driveSubsystem.rightRearDrive.get(), 0.001);
    }
    
    protected void verifyTurningLeft()
    {
        assertTrue(driveSubsystem.leftFrontDrive.get() < 0);
        assertTrue(driveSubsystem.leftRearDrive.get() < 0);
        assertTrue(driveSubsystem.rightFrontDrive.get() > 0);
        assertTrue(driveSubsystem.rightRearDrive.get() > 0);
    }
    
    protected void verifyTurningRight()
    {
        assertTrue(driveSubsystem.leftFrontDrive.get() > 0);
        assertTrue(driveSubsystem.leftRearDrive.get() > 0);
        assertTrue(driveSubsystem.rightFrontDrive.get() < 0);
        assertTrue(driveSubsystem.rightRearDrive.get() < 0);
    }
    
    protected void verifyRobotHeading(double expectedHeading) {
        assertEquals(expectedHeading, pose.getCurrentHeading().getValue(), 0.001);
    }
    
    protected void setMockGyroHeading(double heading) {
        mockRobotIO.setGyroHeading(heading);
    }
    
    protected void changeMockGyroHeading(double delta) {
        double oldHeading = mockRobotIO.getGyroHeading();
        double newHeading = oldHeading + delta;
        setMockGyroHeading(newHeading);
    }
}
    