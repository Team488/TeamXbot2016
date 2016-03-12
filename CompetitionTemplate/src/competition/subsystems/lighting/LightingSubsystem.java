package competition.subsystems.lighting;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XDigitalOutput;
import xbot.common.injection.wpi_factories.WPIFactory;

public class LightingSubsystem extends BaseSubsystem {

    private static Logger log = Logger.getLogger(LightingSubsystem.class);
    
    public enum LightingState {
        Disabled(0),BallCaptured(1);
        
        private int value; 
        private LightingState(int value) { this.value = value; }
    }
    
    public final XDigitalOutput[] outputPins;
    
    @Inject
    public LightingSubsystem(WPIFactory factory) {
        int initialPin = 15;
        int numPins = 4;
        outputPins = new XDigitalOutput[numPins];
        for(int i=0; i < numPins; i++) {
            outputPins[i] = factory.getDigitalOutput(i + initialPin);
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
}
