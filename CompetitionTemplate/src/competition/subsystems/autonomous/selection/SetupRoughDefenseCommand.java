package competition.subsystems.autonomous.selection;

import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;


public class SetupRoughDefenseCommand extends SetupRaiseArmAndTraverseCommand {
    
    final String label = "RoughDefenseAuto ";
    
    @Inject
    public SetupRoughDefenseCommand(XPropertyManager propMan, RaiseArmAndTraverseDefenseCommandGroup auto,
            AutonomousModeSelector autonomousModeSelector) {
        super(propMan, auto, autonomousModeSelector);
    }
}
