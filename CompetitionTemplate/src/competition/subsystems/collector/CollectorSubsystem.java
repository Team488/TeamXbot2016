package competition.subsystems.collector;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XDigitalInput;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class CollectorSubsystem {
    public final XSpeedController collectorMotorLeft;
    
    BooleanProperty ballInCollector;
    
    public XDigitalInput ballExistsSensor;
    
    @Inject
    public CollectorSubsystem(WPIFactory factory, XPropertyManager propMan){
        collectorMotorLeft = factory.getSpeedController(0);
        collectorMotorLeft.setInverted(true);
        ballExistsSensor = factory.getDigitalInput(1);
        ballInCollector = propMan.createEphemeralProperty("BallInCollector", false);
    }
    
    public void setIntakePower(double power) {
        this.collectorMotorLeft.set(power);
    }
    
    public boolean isBallInCollector() {
        boolean isBallIn = !ballExistsSensor.get();
        
        ballInCollector.set(isBallIn);
        return isBallIn;
    }

}
