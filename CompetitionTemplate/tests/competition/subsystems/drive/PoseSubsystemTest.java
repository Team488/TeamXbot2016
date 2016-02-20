package competition.subsystems.drive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PoseSubsystemTest extends DriveTestBase {
        
    @Before
    public void setup() {
        super.setup();
    }    
    
    @Test
    public void testInitialHeading() {
        // IMU initially starts at 0, robot starts at 90.
        verifyRobotHeading(PoseSubsystem.FACING_AWAY_FROM_DRIVERS);
    }
    
    @Test
    public void testGyroRotate() {
        changeMockGyroHeading(45);
        verifyRobotHeading(135);
        
        changeMockGyroHeading(-45);
        verifyRobotHeading(90);
    }
    
    @Test
    public void testCalibrate() {
        changeMockGyroHeading(45);
        verifyRobotHeading(135);
        
        pose.setCurrentHeading(90);
        verifyRobotHeading(90);
    }
    
    @Test
    public void testCalibrateAndMove() {
        changeMockGyroHeading(45);
        verifyRobotHeading(135);
        
        pose.setCurrentHeading(90);
        verifyRobotHeading(90);
        
        changeMockGyroHeading(-45);
        verifyRobotHeading(45);
    }
    
    @Test
    public void testCrossBounds () {
        changeMockGyroHeading(180);
        verifyRobotHeading(-90);
        
        changeMockGyroHeading(100);
        verifyRobotHeading(10);
    }
}

