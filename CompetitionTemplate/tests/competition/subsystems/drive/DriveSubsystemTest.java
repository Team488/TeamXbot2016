package competition.subsystems.drive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import xbot.common.injection.BaseWPITest;

public class DriveSubsystemTest extends BaseDriveTest {
        
    @Before
    public void setup() {
        super.setup();
    }
    
    @Test
    public void testTankDrive() {
        driveSubsystem.tankDrive(0, 0);
        checkChassisPower(0, 0);
        
        driveSubsystem.tankDrive(1, 0.5);
        checkChassisPower(1, 0.5);
    }
    
    @Test
    public void testSafeTankDrive() {
        driveSubsystem.tankDriveSafely(1, 1);
        checkChassisPower(1, 1);
        
        //big tilt upwards
        this.mockRobotIO.setGyroPitch(40);
        driveSubsystem.tankDriveSafely(1, 1);
        double tiltPower = driveSubsystem.tipPreventionPower.get();
        // should try to drive backwards
        checkChassisPower(-tiltPower, -tiltPower);
        
        this.mockRobotIO.setGyroPitch(-40);
        driveSubsystem.tankDriveSafely(1, 1);
        checkChassisPower(tiltPower, tiltPower);
        
        // flip over!
        this.mockRobotIO.setGyroPitch(175);
        driveSubsystem.tankDriveSafely(1, 1);
        checkChassisPower(0, 0);
        
        // tilt back
        this.mockRobotIO.setGyroPitch(40);
        driveSubsystem.tankDriveSafely(1, 1);
        // should try to drive backwards
        checkChassisPower(0, 0);
        
        //get safe
        this.mockRobotIO.setGyroPitch(20);
        driveSubsystem.tankDriveSafely(1, 1);
        // should try to drive backwards
        checkChassisPower(1, 1);
    }
}
