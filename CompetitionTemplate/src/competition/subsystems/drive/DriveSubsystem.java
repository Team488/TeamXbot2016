package competition.subsystems.drive;

import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.logic.Latch;
import xbot.common.logic.Latch.EdgeType;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem implements Observer {
    
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
    
    public BooleanProperty enableSafeTankDrive;
    
    private Latch extremePitchLatch;
        
    @Inject
    public DriveSubsystem(WPIFactory factory, XPropertyManager propManager, PoseSubsystem pose)
    {
        log.info("Creating DriveSubsystem");

        this.leftFrontDrive = factory.getSpeedController(2);
        this.leftRearDrive = factory.getSpeedController(4);
        
        this.leftRearDrive.setInverted(true);
        
        this.rightFrontDrive = factory.getSpeedController(3);
        this.rightRearDrive = factory.getSpeedController(5);
        
        this.rightFrontDrive.setInverted(true);
        
        this.pose = pose;
        tipPreventionEnabled = propManager.createEphemeralProperty("TipPreventionEnabled", true);
        tipPreventionPower = propManager.createPersistentProperty("TipPreventionPower", 1.0);
        
        leftPowerProp = propManager.createEphemeralProperty("LeftPower", 0.0);
        rightPowerProp = propManager.createEphemeralProperty("RightPower", 0.0);
        
        enableSafeTankDrive = propManager.createPersistentProperty("EnableSafeTankDrive", false);
        extremePitchLatch = new Latch(false, EdgeType.Both);
        extremePitchLatch.addObserver(this);
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
        
        
        
        if (enableSafeTankDrive.get()) {
            // if we are incredibly tipped over, don't bother, we can't save ourselves. Also disable safeties,
            // they will be re-enabled once we right ourselves.
            if (isRobotCrazyFlipped()) {
                tippedRecently = true;
                leftPower = 0;
                rightPower = 0;
            }
            // if we are pitching a lot, AND tip prevention is enabled, AND we haven't fully tipped over recently,
            // try and fix the situation using tipPower.
            else if (isRobotInDangerOfFlipping()) {
                double fixPower = fixTipping();
                leftPower = fixPower;
                rightPower = fixPower;
            }
            // if our pitch looks relatively safe
            else if (isRobotSafeToDrive()) {
                tippedRecently = false;
            }
            else if (tippedRecently == true) {
                // we've tipped recently, don't drive!
                leftPower = 0;
                rightPower = 0;
            }
        }
        
        extremePitchLatch.setValue(tippedRecently);
        
        // Drive with the potentially-modified power values.
        tankDrive(leftPower, rightPower);
    }

    private double fixTipping() {
        double tipPower = tipPreventionPower.get();
        double fixPower = 0;
        if (pose.getRobotPitch() > 0) {            
            fixPower = -tipPower;
        }
        else
        {
            fixPower = tipPower;
        }
        return fixPower;
    }

    private boolean isRobotSafeToDrive() {
        return Math.abs(pose.getRobotPitch()) <= 30;
    }

    private boolean isRobotInDangerOfFlipping() {
        return Math.abs(pose.getRobotPitch()) > 30 
                && tipPreventionEnabled.get() == true 
                && tippedRecently == false;
    }

    private boolean isRobotCrazyFlipped() {
        return Math.abs(pose.getRobotPitch()) > 90;
    }
    
    public void tankRotateSafely(double rotationalPower) {
        tankDriveSafely(-rotationalPower, rotationalPower);
    }
    
    public void stopDrive() {
        tankDrive(0, 0);
    }

    @Override
    public void update(Observable o, Object arg) {
        switch((EdgeType)arg) {
        case FallingEdge:
            log.warn("Robot in extreme pitch state!");
            break;
        case RisingEdge:
            log.info("Robot has returned to normal pitch state.");
            break;
        default:
            break;
        }
    }
}
