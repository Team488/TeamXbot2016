package competition.subsystems.lighting;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XDigitalOutput;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class LightingSubsystem extends BaseSubsystem {

    private static Logger log = Logger.getLogger(LightingSubsystem.class);
    
    private DoubleProperty ledSignalProp;
    private BooleanProperty enableVisionLEDProp;
    
    public static final int initialOutputPin = 19;
    public static final int numOutputPins = 4;
    
    public static enum LightingState {
        Disabled,
        Enabled,
        BallCaptured
    }
    
    public static enum BallDetectionDirection {
        None,
        Center,
        Left,
        Right
    }
    
    protected boolean isRobotEnabled = false;
    protected Alliance currentAlliance = Alliance.Blue;
    protected BallDetectionDirection currentBallDirection = BallDetectionDirection.None;
    
    protected LightingState currentLightingState = LightingState.Disabled;

    public final XDigitalOutput[] outputPins;
    
    @Inject
    public LightingSubsystem(WPIFactory factory, XPropertyManager propMan) {
        ledSignalProp = propMan.createEphemeralProperty("LED signal state", 0);
        enableVisionLEDProp = propMan.createPersistentProperty("Enable vision on LEDs", true);
        
        outputPins = new XDigitalOutput[numOutputPins];
        for(int i = 0; i < numOutputPins; i++) {
            outputPins[i] = factory.getDigitalOutput(i + initialOutputPin);
        }
    }
    
    public void setLightingState(LightingState state) {
        this.currentLightingState = state;
        
        updateLightingIO();
    }
    
    protected void updateLightingIO() {
        byte outputSignal = 0;
        
        if(currentLightingState == LightingState.Disabled) {
            // Robot is disabled
            outputSignal = 0;
        }
        else if(currentLightingState == LightingState.BallCaptured) {
            // Robot has ball captured
            outputSignal = 1;
        }
        else if (currentLightingState == LightingState.Enabled) {            
            if(currentAlliance == Alliance.Red) {
                // Robot is on the red alliance
                outputSignal = 6;
            }
            else {
                // Robot is on the blue alliance
                outputSignal = 2;
            }

            outputSignal += getDirectionSignalOffset();
        }
        
        this.setLEDData(outputSignal);
    }
    
    /**
     * Gets an offset corresponding to the current ball detection direction.
     * @return a value from 1 to 3 indicating the current ball direction, or 0 if no ball is being tracked.
     */
    protected byte getDirectionSignalOffset() {
        if(!enableVisionLEDProp.get()) {
            // LED indication of vision info is disabled
            return 0;
        }
        
        if(currentBallDirection == BallDetectionDirection.Center) {
            // Vision detects ball within center range
            return 1;
        }
        else if(currentBallDirection == BallDetectionDirection.Left) {
            // Vision detects ball within left range
            return 2;
        }
        else if (currentBallDirection == BallDetectionDirection.Right) {
            // Vision detects ball within right range
            return 3;
        }
        else {
            // Vision doesn't detect any balls
            return 0;
       }
    }
    
    public void setLEDData(byte data)
    {
        setLEDData((int)data);
    }
    
    public void setLEDData(int data)
    {
        if(data >> outputPins.length != 0)
        {
            log.warn("LED data lost (not enough pins!)");
        }

        // Send over DIO
        for(int i = 0; i < outputPins.length; i++)
        {
            boolean bitValue = (data & (1 << i)) != 0;
            outputPins[i].set(bitValue);
        }
        
        // Send over Network Tables (in Smart Dashboard table)
        ledSignalProp.set(data);
    }
    
    public boolean isRobotEnabled() {
        return isRobotEnabled;
    }

    public void setRobotEnabled(boolean isRobotEnabled) {
        this.isRobotEnabled = isRobotEnabled;
        
        updateLightingIO();
    }
    
    public void setCurrentAlliance(Alliance alliance) {
        this.currentAlliance = alliance;
        
        updateLightingIO();
    }
    
    public void setCurrentBallDirection(BallDetectionDirection direction) {
        this.currentBallDirection = direction;
        
        updateLightingIO();
    }
}
