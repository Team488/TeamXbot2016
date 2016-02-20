package competition.subsystems.portcullis;

import org.junit.Before;
import org.junit.Test;

import competition.BaseRobotTest;
import competition.subsystems.portcullis_wheels.PortcullisWheelsSubsystem;
import static org.junit.Assert.*;

public class PortcullisSubsystemTests extends BaseRobotTest {
    
    PortcullisWheelsSubsystem wheels;
    
    @Before
    public void setup() {
        super.setUp();
        
        this.wheels = injector.getInstance(PortcullisWheelsSubsystem.class);
    }
    
    @Test
    public void testRaise() {
        wheels.setWheelSpeed(1);
        
        assertEquals(wheels.portcullisWheel.get(), 1d, 0.001);
    }
    
    @Test
    public void testLower() {
        wheels.setWheelSpeed(-1);
        assertEquals(wheels.portcullisWheel.get(), -1d, 0.001);
    }
}
