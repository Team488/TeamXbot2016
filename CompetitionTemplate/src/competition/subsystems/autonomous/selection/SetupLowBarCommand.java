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
        
        traverseDefenseHeading = propMan.createPersistentProperty(label + "traverseDefenseHeading", 90.0);
        traverseDefensePower = propMan.createPersistentProperty(label + "traverseDefensePower", 0.75);
        autoArmGoal = propMan.createPersistentProperty(label + "autonomousArmGoal", 30.0);
        traverseMinTime = propMan.createPersistentProperty(label + "traverseDefenseMinTime", 1.0);
        traverseMaxTime = propMan.createPersistentProperty(label + "traverseDefenseMaxTime", 3.0);
        this.auto.label = this.label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
