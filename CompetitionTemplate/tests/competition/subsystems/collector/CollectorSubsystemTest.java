package competition.subsystems.collector;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.BaseRobotTest;
import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.collector.commands.CollectorIntakeCommand;
import edu.wpi.first.wpilibj.MockDigitalInput;
import xbot.common.injection.BaseWPITest;

public class CollectorSubsystemTest extends BaseRobotTest {
    CollectorSubsystem collectorSubsystem;
    
    @Before
    public void setup() {
        this.collectorSubsystem = this.injector.getInstance(CollectorSubsystem.class);
    }
    
    private double getCollectorPower() {
        return collectorSubsystem.collectorMotorLeft.get();
    }
    
    @Test
    public void testCollectorEjectCommand() {
         CollectorEjectCommand collectorEjectCommand = this.injector.getInstance(CollectorEjectCommand.class);
         
         collectorEjectCommand.initialize();
         collectorEjectCommand.execute();

         assertTrue(getCollectorPower() < 0);
         
         collectorEjectCommand.end();
         
         assertTrue(getCollectorPower() == 0);
    }
    
    @Test
    public void testCollectorIntakeCommand() {
         CollectorIntakeCommand collectorIntakeCommand = this.injector.getInstance(CollectorIntakeCommand.class);
         ((MockDigitalInput)collectorSubsystem.ballExistsSensor).set_value(true);
         collectorIntakeCommand.execute();
         
         assertTrue(getCollectorPower() > 0);
         
         collectorIntakeCommand.end();
         
         assertTrue(getCollectorPower() == 0);
    }
    
    @Test
    public void testCollectorStopsIntaking() {
        CollectorIntakeCommand collectorIntakeCommand = this.injector.getInstance(CollectorIntakeCommand.class);
        ((MockDigitalInput)collectorSubsystem.ballExistsSensor).set_value(true);
        collectorIntakeCommand.execute();
        
        assertTrue(getCollectorPower() > 0);
        
        setIsBallInCollector(true);
        
        collectorIntakeCommand.enableCollectorAutoStop.set(true);
        collectorIntakeCommand.execute();
        
        assertTrue(getCollectorPower() == 0);
    }
    
    private void setIsBallInCollector(boolean value) {
        ((MockDigitalInput)collectorSubsystem.ballExistsSensor).set_value(!value);
    }
    
}