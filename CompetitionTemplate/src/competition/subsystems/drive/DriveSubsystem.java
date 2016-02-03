package competition.subsystems.drive;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    
    private static Logger log = Logger.getLogger(DriveSubsystem.class);
    
    public final XSpeedController leftFrontDrive; 
    public final XSpeedController leftRearDrive; 
    public final XSpeedController rightFrontDrive; 
    public final XSpeedController rightRearDrive; 
    
    private PoseSubsystem pose;
    public BooleanProperty tipPreventionEnabled;
    public DoubleProperty tipPreventionPower;
    private boolean tippedRecently = false;
    
    private DoubleProperty leftPowerProp;
    private DoubleProperty rightPowerProp;
        
    @Inject
    public DriveSubsystem(WPIFactory factory, PropertyManager propManager, PoseSubsystem pose)
    {
        log.info("Creating DriveSubsystem");

        this.leftFrontDrive = factory.getSpeedController(0);
        this.leftRearDrive = factory.getSpeedController(1);
        
        this.rightFrontDrive = factory.getSpeedController(2);
        this.rightRearDrive = factory.getSpeedController(3);
        
        this.leftFrontDrive.setInverted(true);
        this.leftRearDrive.setInverted(true);
        
        this.pose = pose;
        tipPreventionEnabled = propManager.createEphemeralProperty("TipPreventionEnabled", true);
        tipPreventionPower = propManager.createPersistentProperty("TipPreventionPower", 1.0);
        
        leftPowerProp = propManager.createEphemeralProperty("LeftPower", 0.0);
        rightPowerProp = propManager.createEphemeralProperty("RightPower", 0.0);
    }
    
    public void tankDrive(double leftPower, double rightPower) {
        
        this.leftFrontDrive.set(leftPower);
        this.leftRearDrive.set(leftPower);
        
        this.rightFrontDrive.set(rightPower);
        this.rightRearDrive.set(rightPower);
        
        leftPowerProp.set(leftPower);
        rightPowerProp.set(rightPower);
    }
    
    public void tankDriveSafely(double leftPower, double rightPower) {
        
        
        // if we are incredibly tipped over, don't bother, we can't save ourselves. Also disable safeties,
        // they will be re-enabled once we right ourselves.
        if (Math.abs(pose.getRobotPitch()) > 90) {
            tippedRecently = true;
            leftPower = 0;
            rightPower = 0;
        }
        // if we are pitching a lot, AND tip prevention is enabled, AND we haven't fully tipped over recently,
        // try and fix the situation using tipPower.
        else if (Math.abs(pose.getRobotPitch()) > 30 
                && tipPreventionEnabled.get() == true 
                && tippedRecently == false) {
            double tipPower = tipPreventionPower.get();
            if (pose.getRobotPitch() > 0) {            
                leftPower = -tipPower;
                rightPower = -tipPower;
            }
            else
            {
                leftPower = tipPower;
                rightPower = tipPower;
            }
        }
        // if our pitch looks relatively safe
        else if (Math.abs(pose.getRobotPitch()) <= 30) {
            tippedRecently = false;
        }
        else if (tippedRecently == true) {
            // we've tipped recently, don't drive!
            leftPower = 0;
            rightPower = 0;
        }
        
        // Drive with the potentially-modified power values.
        tankDrive(leftPower, rightPower);
    }
}
