package competition.subsystems.collector;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class CollectorSubsystem {
    public final XSpeedController collectorMotorLeft;
    
    @Inject
    public CollectorSubsystem(WPIFactory factory){
        this.collectorMotorLeft = factory.getSpeedController(1);
        this.collectorMotorLeft.setInverted(true);
    }
    
    public void setIntakePower(double power) {
        this.collectorMotorLeft.set(power);
    }

}
