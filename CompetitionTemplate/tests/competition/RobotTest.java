package competition;

import org.junit.Test;

import xbot.common.injection.BaseWPITest;
import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;

public class RobotTest extends BaseWPITest{
    @Test
    public void testDefaultSystem() {
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
    }
}
