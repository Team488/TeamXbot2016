package competition.subsystems.autonomous.selection;

import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;
import competition.subsystems.drive.PoseSubsystem;


public class SetupRaiseArmAndTraverseCommand extends BaseAutonomousModeSetCommand {

    RaiseArmAndTraverseDefenseCommandGroup auto;
    
    final DoubleProperty traverseDefensePower;
    final DoubleProperty traverseDefenseHeading;
    final DoubleProperty autoArmGoal;
    final DoubleProperty traverseMinTime;
    final DoubleProperty traverseMaxTime;
    
    final String label = "";
    
    @Inject
    public SetupRaiseArmAndTraverseCommand(XPropertyManager propMan, 
            RaiseArmAndTraverseDefenseCommandGroup auto,
            AutonomousModeSelector autonomousModeSelector) {
        super(autonomousModeSelector);
        
        traverseDefenseHeading = propMan.createPersistentProperty(label + "traverseDefenseHeading", 90.0);
        traverseDefensePower = propMan.createPersistentProperty(label + "traverseDefensePower", 0.75);
        autoArmGoal = propMan.createPersistentProperty(label + "autonomousArmGoal", 30.0);
        traverseMinTime = propMan.createPersistentProperty(label + "traverseDefenseMinTime", 1.0);
        traverseMaxTime = propMan.createPersistentProperty(label + "traverseDefenseMaxTime", 3.0);
        
        this.auto = auto;
    }

    @Override
    public void initialize() {
        auto.setTraversalProperties(
                traverseDefensePower.get(),
                traverseDefenseHeading.get(),
                traverseMinTime.get(),
                traverseMaxTime.get());
        auto.setArmAngle(autoArmGoal.get());
        auto.setInitialHeading(traverseDefenseHeading.get());
        
        this.autonomousModeSelector.setCurrentAutonomousCommand(auto);
    }
}
