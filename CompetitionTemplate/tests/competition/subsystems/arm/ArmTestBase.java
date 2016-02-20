package competition.subsystems.arm;

import static org.junit.Assert.*;

import org.junit.Before;

import competition.BaseRobotTest;
import edu.wpi.first.wpilibj.MockDigitalInput;
import edu.wpi.first.wpilibj.MockEncoder;

public class ArmTestBase extends BaseRobotTest {
    
    protected ArmSubsystem armSubsystem;
    
    @Before
    public void setup() {
        this.armSubsystem = this.injector.getInstance(ArmSubsystem.class);
    }
    
    protected void setMockEncoder(double value) {
        ((MockEncoder)armSubsystem.encoder).setDistance(value);
    }
    
    protected void setLimitSwitches(boolean up, boolean down) {
        ((MockDigitalInput)armSubsystem.upperLimitSwitch).set_value(up);
        ((MockDigitalInput)armSubsystem.lowerLimitSwitch).set_value(down);
    }
}