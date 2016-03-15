package competition.subsystems.lighting;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XDigitalOutput;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class LightingSubsystem extends BaseSubsystem {

    private static Logger log = Logger.getLogger(LightingSubsystem.class);
    
    public static final int initialOutputPin = 2;
    public static final int numOutputPins = 2;
    
    public enum LightingState {
        Disabled(0), BallCaptured(1), Enabled(2);
        
        private int value; 
        private LightingState(int value) { this.value = value; }
    }
    
    protected boolean isRobotEnabled = false;

    public final XDigitalOutput[] outputPins;
    
    @Inject
    public LightingSubsystem(WPIFactory factory) {
        outputPins = new XDigitalOutput[numOutputPins];
        for(int i = 0; i < numOutputPins; i++) {
            outputPins[i] = factory.getDigitalOutput(i + initialOutputPin);
        }
    }
    
    public void setLightingState(LightingState state) {
        this.setLEDData(state.value);
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
        
        for(int i = 0; i < outputPins.length; i++)
        {
            outputPins[i].set((data & (1 << i)) != 0);
        }
    }
    
    public boolean isRobotEnabled() {
        return isRobotEnabled;
    }

    public void setRobotEnabled(boolean isRobotEnabled) {
        this.isRobotEnabled = isRobotEnabled;
    }
}
