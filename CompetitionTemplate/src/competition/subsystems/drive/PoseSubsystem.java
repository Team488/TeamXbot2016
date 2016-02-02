package competition.subsystems.drive;

import java.util.function.DoubleFunction;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.sensors.DistanceSensor;
import xbot.common.controls.sensors.NavImu.ImuType;
import xbot.common.controls.sensors.XGyro;
import xbot.common.controls.sensors.AnalogDistanceSensor.VoltageMaps;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.math.ContiguousDouble;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;

@Singleton
public class PoseSubsystem extends BaseSubsystem {
    
    private static Logger log = Logger.getLogger(PoseSubsystem.class);
    public XGyro imu;
    public DistanceSensor leftDistanceSensor;
    private ContiguousDouble currentHeading;
    private DoubleProperty currentHeadingProp;
    
    private ContiguousDouble lastImuHeading;
    
    private DoubleProperty leftSensorMountingDistanceInches;
    
    public static final double FACING_AWAY_FROM_DRIVERS = 90;
    
    @Inject
    public PoseSubsystem(WPIFactory factory, PropertyManager propManager) {
        log.info("Creating PoseSubsystem");
        imu = factory.getGyro(ImuType.navX);
        leftDistanceSensor = factory.getAnalogDistanceSensor(1, voltage -> TemporaryVoltageMap.placeholder(voltage));
        leftSensorMountingDistanceInches = propManager.createPersistentProperty("LeftSensorMountingDistanceInches", 16.0);
        currentHeadingProp = propManager.createEphemeralProperty("CurrentHeading", 0.0);
        // Right when the system is initialized, we need to have the old value be
        // the same as the current value, to avoid any sudden changes later
        lastImuHeading = imu.getYaw();
        currentHeading = new ContiguousDouble(FACING_AWAY_FROM_DRIVERS, -180, 180);
    }
    
    public static class TemporaryVoltageMap
    {
        public static final double placeholder(double voltage)
        {
            return 10*voltage;
        }
    }
    
    /**
     * Will automatically be called by GetCurrentHeading, so this is only exposed so that you could
     * continue to update the property when the robot doesn't explicitly need it - such as when the robot 
     * is disabled, but the drivers/programmers want to see the robot heading
     */
    public void updateCurrentHeading() {
        // Old heading - current heading gets the delta heading        
        double imuDeltaYaw = lastImuHeading.difference(imu.getYaw());

        // add the delta to our current
        currentHeading.shiftValue(imuDeltaYaw);
        
        // update the "old" value
        lastImuHeading = imu.getYaw();
        
        currentHeadingProp.set(currentHeading.getValue());
    }
    
    public ContiguousDouble getCurrentHeading() {
        updateCurrentHeading();
        return currentHeading;
    }
    
    public void setCurrentHeading(double headingInDegrees){
        currentHeading.setValue(headingInDegrees);
    }
    
    public double getFrontRangefinderDistance() {
        return 0;
    }
    
    public PoseResult getDistanceFromLeftRangerfinderToLeftWall() {
        // We want to return the distance between the center of rotation of the robot, and whatever the rangefinder
        // is hitting. This involves some trig.
        // the core forumla - (rangefinder distance + distance from sensor mount to center of rotation) * abs(sin(heading))
        
        // These results are only valid if the robot's left side is already kind of pointed at the wall. Otherwise there's
        // too much speculation.
        
        double compensatedRange =(leftSensorMountingDistanceInches.get() + leftDistanceSensor.getDistance()) 
                                * Math.abs(Math.sin(getCurrentHeading().getValue()));
        boolean sane = true;
        if (Math.abs(getCurrentHeading().getValue()) < 60) {
            sane = false;
        }
        
        return new PoseResult(sane, compensatedRange);        
    }
    
    public double getRobotPitch() {
        return imu.getPitch();
    }
    
    public double getRobotRoll() {
        return imu.getRoll();
    }
}