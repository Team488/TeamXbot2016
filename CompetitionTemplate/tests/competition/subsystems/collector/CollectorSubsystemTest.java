package competition.subsystems.collector;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.subsystems.collector.commands.CollectorEjectCommand;
import competition.subsystems.collector.commands.CollectorIntakeCommand;
import xbot.common.injection.BaseWPITest;

public class CollectorSubsystemTest extends BaseWPITest{
    CollectorSubsystem collectorSubsystem;
    
    @Before
    public void setup() {
        this.collectorSubsystem = this.injector.getInstance(CollectorSubsystem.class);
    }
    
    @Test
    public void testCollectorEjectCommand() {
         CollectorEjectCommand collectorEjectCommand = this.injector.getInstance(CollectorEjectCommand.class);
         
         collectorEjectCommand.initialize();
         
         assertTrue(collectorSubsystem.collectorMotor.get() < 0);
         
         collectorEjectCommand.end();
         
         assertTrue(collectorSubsystem.collectorMotor.get() == 0);
    }
    
    @Test
    public void testCollectorIntakeCommand() {
         CollectorIntakeCommand collectorIntakeCommand = this.injector.getInstance(CollectorIntakeCommand.class);
         
         collectorIntakeCommand.initialize();
         
         assertTrue(collectorSubsystem.collectorMotor.get() > 0);
         
         collectorIntakeCommand.end();
         
         assertTrue(collectorSubsystem.collectorMotor.get() == 0);
    }
    
}