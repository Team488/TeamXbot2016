package competition.subsystems.autonomous.selection;

import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;


public class SetupLowBarCommand extends SetupRaiseArmAndTraverseCommand {
    
    final String label = "LowBarAuto ";
    
    @Inject
    public SetupLowBarCommand(XPropertyManager propMan, RaiseArmAndTraverseDefenseCommandGroup auto,
            AutonomousModeSelector autonomousModeSelector) {
        super(propMan, auto, autonomousModeSelector);
    }
}
