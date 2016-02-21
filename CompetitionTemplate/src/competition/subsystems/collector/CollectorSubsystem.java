package competition.subsystems.collector;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XDigitalInput;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class CollectorSubsystem {
    public final XSpeedController collectorMotorLeft;
    
    public XDigitalInput ballExistsSensor;
    
    @Inject
    public CollectorSubsystem(WPIFactory factory){
        this.collectorMotorLeft = factory.getSpeedController(1);
        this.ballExistsSensor = factory.getDigitalInput(1);
    }
    
    public void setIntakePower(double power) {
        this.collectorMotorLeft.set(power);
    }
    
    public boolean isBallInCollector() {
        return ballExistsSensor.get();
    }

}
