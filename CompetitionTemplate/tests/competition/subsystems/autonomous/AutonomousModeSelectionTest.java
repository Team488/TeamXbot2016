package competition.subsystems.autonomous;

import static org.junit.Assert.*;

import org.junit.Test;

import competition.subsystems.autonomous.selection.AutonomousModeSelector;
import competition.subsystems.autonomous.selection.SetupRoughDefenseForwardsCommand;
import competition.subsystems.autonomous.selection.SetupTraverseDefenseCommand;
import xbot.common.injection.BaseWPITest;

public class AutonomousModeSelectionTest extends BaseWPITest {

    @Test
    public void test() {
        AutonomousModeSelector selector = this.injector.getInstance(AutonomousModeSelector.class);
        
        SetupRoughDefenseForwardsCommand setupCommand = this.injector.getInstance(SetupRoughDefenseForwardsCommand.class);
        
        assertEquals(null, selector.getCurrentAutonomousCommand());
        
        setupCommand.initialize();
        
        assertEquals(setupCommand.auto, selector.getCurrentAutonomousCommand());
        
        assertEquals(setupCommand.auto.toString(), selector.currentAutonomousCommandName.get());
        
    }

}
