package competition.subsystems.collector;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.wpilibj.command.Subsystem;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSpeedController;
import xbot.common.controls.sensors.XDigitalInput;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

@Singleton
public class CollectorSubsystem  extends BaseSubsystem {
    public final XSpeedController collectorMotorLeft;
    DoubleProperty ejectPower;
    DoubleProperty intakePower;
    
    BooleanProperty ballInCollector;
    
    public XDigitalInput ballExistsSensor;
    
    @Inject
    public CollectorSubsystem(WPIFactory factory, XPropertyManager propMan){
        this.collectorMotorLeft = factory.getSpeedController(0);
        this.collectorMotorLeft.setInverted(true);
        this.ballExistsSensor = factory.getDigitalInput(1);
        
        this.ballInCollector = propMan.createEphemeralProperty("BallInCollector", false);

        intakePower = propMan.createPersistentProperty("Collector intake power", 1.0);
        ejectPower = propMan.createPersistentProperty("Collector eject power", -1.0);
    }
    
    public void setCollectorPower(double power) {
        this.collectorMotorLeft.set(power);
    }
    
    public void eject() {
        setCollectorPower(ejectPower.get());
    }
    
    public void intake() {
        setCollectorPower(intakePower.get());
    }
    
    public void stopCollector() {
        setCollectorPower(0);
    }
    
    public void updateBoulderSensor() {
        boolean isBallIn = !ballExistsSensor.get();
        ballInCollector.set(isBallIn);
    }
    
    public boolean isBallInCollector() {
        updateBoulderSensor();
        return ballInCollector.get();
    }

}
