package competition.subsystems.drive;

import java.util.function.DoubleFunction;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.sensors.DistanceSensor;
import xbot.common.controls.sensors.XEncoder;
import xbot.common.controls.sensors.NavImu.ImuType;
import xbot.common.controls.sensors.XGyro;
import xbot.common.controls.sensors.AnalogDistanceSensor.VoltageMaps;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.math.ContiguousDouble;
import xbot.common.math.ContiguousHeading;
import xbot.common.math.XYPair;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class PoseSubsystem extends BaseSubsystem {
        
    private static Logger log = Logger.getLogger(PoseSubsystem.class);
    public XGyro imu;
    
    public DistanceSensor frontDistanceSensor;
    public DistanceSensor rearDistanceSensor;
    public DistanceSensor leftDistanceSensor;
    public DistanceSensor rightDistanceSensor;
    
    public XEncoder leftDriveEncoder;
    public XEncoder rightDriveEncoder;
    
    private DoubleProperty leftDriveDistance;
    private DoubleProperty rightDriveDistance;
    
    private DoubleProperty totalDistanceX;
    private DoubleProperty totalDistanceY;
    
    private ContiguousHeading currentHeading;
    private DoubleProperty currentHeadingProp;
    
    private ContiguousHeading lastImuHeading;
    
    private DoubleProperty leftSensorMountingDistanceInches;
    
    private DoubleProperty frontDistance;
    private DoubleProperty rearDistance;
    private DoubleProperty leftDistance;
    private DoubleProperty rightDistance;
    
    // These are two common robot starting positions - kept here as convenient shorthand.
    public static final double FACING_AWAY_FROM_DRIVERS = 90;
    public static final double FACING_TOWARDS_DRIVERS = -90;
    
    private DoubleProperty currentPitch;
    private DoubleProperty currentRoll;
    private DoubleProperty leftDistanceToWall;
    
    @Inject
    public PoseSubsystem(WPIFactory factory, XPropertyManager propManager) {
        log.info("Creating PoseSubsystem");
        imu = factory.getGyro(ImuType.navX);
        frontDistanceSensor = factory.getAnalogDistanceSensor(1, voltage -> TemporaryVoltageMap.placeholder(voltage));
        rearDistanceSensor = factory.getAnalogDistanceSensor(2, voltage -> TemporaryVoltageMap.placeholder(voltage));
        leftDistanceSensor = factory.getAnalogDistanceSensor(3, voltage -> TemporaryVoltageMap.placeholder(voltage));
        rightDistanceSensor = factory.getAnalogDistanceSensor(4, voltage -> TemporaryVoltageMap.placeholder(voltage));
        
        
        leftSensorMountingDistanceInches = propManager.createPersistentProperty("LeftSensorMountingDistanceInches", 16.0);
        currentHeadingProp = propManager.createEphemeralProperty("CurrentHeading", 0.0);
        // Right when the system is initialized, we need to have the old value be
        // the same as the current value, to avoid any sudden changes later
        lastImuHeading = imu.getYaw();
        currentHeading = new ContiguousHeading(FACING_AWAY_FROM_DRIVERS);
        
        currentPitch = propManager.createEphemeralProperty("CurrentPitch", 0.0);
        currentRoll = propManager.createEphemeralProperty("CurrentRoll", 0.0);
        
        frontDistance = propManager.createEphemeralProperty("FrontDistance", 0.0);
        rearDistance = propManager.createEphemeralProperty("RearDistance", 0.0);
        leftDistance = propManager.createEphemeralProperty("LeftDistance", 0.0);
        rightDistance = propManager.createEphemeralProperty("RightDistance", 0.0);
        
        leftDistanceToWall = propManager.createEphemeralProperty("LeftDistanceToWall", 0.0);
        
        leftDriveEncoder = factory.getEncoder(8, 9);
        rightDriveEncoder = factory.getEncoder(6, 7);
        rightDriveEncoder.setDistancePerPulse(-1);
        
        leftDriveDistance = propManager.createEphemeralProperty("LeftDriveDistance", 0.0);
        rightDriveDistance = propManager.createEphemeralProperty("RightDriveDistance", 0.0);
        
        totalDistanceX = propManager.createEphemeralProperty("TotalDistanceX", 0.0);
        totalDistanceY = propManager.createEphemeralProperty("TotalDistanceY", 0.0);
    }
    
    public static class TemporaryVoltageMap
    {
        public static final double placeholder(double voltage)
        {
            return 1*voltage;
        }
    }
    
    /**
     * Will automatically be called by GetCurrentHeading, so this is only exposed so that you could
     * continue to update the property when the robot doesn't explicitly need it - such as when the robot 
     * is disabled, but the drivers/programmers want to see the robot heading
     */
    private void updateCurrentHeading() {
        // Old heading - current heading gets the delta heading        
        double imuDeltaYaw = lastImuHeading.difference(imu.getYaw());

        // add the delta to our current
        currentHeading.shiftValue(imuDeltaYaw);
        
        // update the "old" value
        lastImuHeading = imu.getYaw();
        
        currentHeadingProp.set(currentHeading.getValue());
        
        currentPitch.set(imu.getPitch());
        currentRoll.set(imu.getRoll());
    }
    
    public ContiguousHeading getCurrentHeading() {
        updateCurrentHeading();
        return currentHeading;
    }
    
    public XYPair getTotalDistanceTraveled() {
        return new XYPair(0,0);
    }
    
    public void setCurrentHeading(double headingInDegrees){
        currentHeading.setValue(headingInDegrees);
    }
    
    private void updateRangefinders() {
        frontDistance.set(getFrontRangefinderDistance());
        rearDistance.set(getRearRangefinderDistance());
        leftDistance.set(getLeftRangefinderDistance());
        rightDistance.set(getRightRangefinderDistance());
                
        getDistanceFromLeftRangerfinderToLeftWall();
    }
    
    private void updateEncoders() {
        leftDriveDistance.set(leftDriveEncoder.getDistance());
        rightDriveDistance.set(rightDriveEncoder.getDistance());
    }
    
    public void updateAllSensors() {
        updateRangefinders();
        updateCurrentHeading();
        updateEncoders();
    }
    
    public double getFrontRangefinderDistance() {
        return frontDistanceSensor.getDistance();
    }
    
    public double getLeftRangefinderDistance() {
        return leftDistanceSensor.getDistance();
    }
    
    public double getRightRangefinderDistance() {
        return rightDistanceSensor.getDistance();
    }
    
    public double getRearRangefinderDistance() {
        return rearDistanceSensor.getDistance();
    }
    
    public PoseResult getDistanceFromLeftRangerfinderToLeftWall() {
        // We want to return the distance between the center of rotation of the robot, and whatever the rangefinder
        // is hitting. This involves some trig.
        // the core forumla - (rangefinder distance + distance from sensor mount to center of rotation) * abs(sin(heading))
        
        // These results are only valid if the robot's left side is already kind of pointed at the wall. Otherwise there's
        // too much speculation.
        
        double compensatedRange =(leftSensorMountingDistanceInches.get() + frontDistanceSensor.getDistance()) 
                                * Math.abs(Math.sin(getCurrentHeading().getValue() * Math.PI / 180));
        boolean sane = true;
        if (Math.abs(getCurrentHeading().getValue()) < 60) {
            sane = false;
        }
        
        leftDistanceToWall.set(compensatedRange);
        
        return new PoseResult(sane, compensatedRange);        
    }
    
    public double getRobotPitch() {
        return imu.getPitch();
    }
    
    public double getRobotRoll() {
        return imu.getRoll();
    }
}