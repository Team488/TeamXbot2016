package competition.subsystems.portcullis;

import org.junit.Before;
import org.junit.Test;

import competition.BaseRobotTest;
import competition.subsystems.portcullis_wheels.PortcullisWheelsSubsystem;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand;
import competition.subsystems.portcullis_wheels.commands.SpinPortcullisWheelsCommand.PortcullisDirection;
import static org.junit.Assert.*;

public class SpinPortcullisWheelsCommandTest extends BaseRobotTest {
    
    PortcullisWheelsSubsystem wheels;
    SpinPortcullisWheelsCommand spin;
    
    @Before
    public void setup() {
        super.setUp();
        
        this.wheels = injector.getInstance(PortcullisWheelsSubsystem.class);
        this.spin = injector.getInstance(SpinPortcullisWheelsCommand.class);
    }
    
    @Test
    public void testRaise() {
        spin.setDirection(PortcullisDirection.Up);
        spin.execute();
        assertEquals(wheels.portcullisWheel.get(), 1d, 0.001);
    }
    
    @Test
    public void testLower() {
        spin.setDirection(PortcullisDirection.Down);
        spin.execute();
        assertEquals(wheels.portcullisWheel.get(), -1d, 0.001);
    }
    
    @Test
    public void testStop() {
        spin.setDirection(PortcullisDirection.Stop);
        spin.execute();
        assertEquals(wheels.portcullisWheel.get(), 0, 0.001);
    }
}
