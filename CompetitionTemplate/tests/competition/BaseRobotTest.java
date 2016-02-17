package competition;

import org.junit.Ignore;

import com.google.inject.Inject;

import competition.subsystems.vision.InertJetsonServer;
import competition.subsystems.vision.JetsonServer;
import xbot.common.injection.BaseWPITest;
import xbot.common.injection.UnitTestModule;

@Ignore
public class BaseRobotTest extends BaseWPITest {
    @Inject
    public BaseRobotTest() {
        this.guiceModule = new UnitTestModule() {
            @Override
            protected void configure() {
                this.bind(JetsonServer.class).to(InertJetsonServer.class);
                super.configure();
            }
        };
    }
}
