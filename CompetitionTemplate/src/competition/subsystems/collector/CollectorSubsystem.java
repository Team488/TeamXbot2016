package competition.subsystems.collector;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class CollectorSubsystem {
    public final XSpeedController collectorMotor;
    
    @Inject
    public CollectorSubsystem(WPIFactory factory){
        this.collectorMotor = factory.getSpeedController(5);
    }
    
    public void setIntakePower(double power) {
        this.collectorMotor.set(power);
    }

}
