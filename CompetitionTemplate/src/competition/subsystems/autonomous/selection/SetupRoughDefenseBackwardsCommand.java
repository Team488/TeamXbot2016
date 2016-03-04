package competition.subsystems.autonomous.selection;

import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;


public class SetupRoughDefenseBackwardsCommand extends SetupRaiseArmAndTraverseCommand {
    
    final String label = "RoughDefenseBackwardsAuto ";
    
    @Inject
    public SetupRoughDefenseBackwardsCommand(XPropertyManager propMan, RaiseArmAndTraverseDefenseCommandGroup auto,
            AutonomousModeSelector autonomousModeSelector) {
        super(propMan, auto, autonomousModeSelector);
    }
}
