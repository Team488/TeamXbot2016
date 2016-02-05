package competition.subsystems.collector;

import xbot.common.controls.actuators.XSpeedController;
import xbot.common.injection.wpi_factories.WPIFactory;

public class CollectorSubsystem {
    public final XSpeedController collectorMotor;
    
    public CollectorSubsystem(WPIFactory factory){
        this.collectorMotor = factory.getSpeedController(5);
    }
    
    public void setIntakePower(double power) {
        this.collectorMotor.set(power);
    }

}
