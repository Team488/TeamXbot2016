package competition.subsystems.collector;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

@Singleton
public class CollectorSubsystem {
    public final XSpeedController collectorMotorLeft;
    public final XSpeedController collectorMotorRight;
    
    @Inject
    public CollectorSubsystem(WPIFactory factory){
        this.collectorMotorLeft = factory.getSpeedController(0);
        this.collectorMotorLeft.setInverted(true);
        
        this.collectorMotorRight = factory.getSpeedController(1);
    }
    
    public void setIntakePower(double power) {
        this.collectorMotorLeft.set(power);
        this.collectorMotorRight.set(power);
    }

}
