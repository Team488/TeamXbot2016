package competition.subsystems.autonomous.selection;

import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;
import competition.subsystems.drive.commands.TraverseDefenseCommand;


public class SetupRaiseArmAndTraverseCommand extends BaseAutonomousModeSetCommand {

    RaiseArmAndTraverseDefenseCommandGroup auto;
    
    final DoubleProperty traverseDefensePower;
    final DoubleProperty traverseDefenseHeading;
    final DoubleProperty autoArmGoal;
    final DoubleProperty traverseMinTime;
    final DoubleProperty traverseMaxTime;
    
    @Inject
    public SetupRaiseArmAndTraverseCommand(XPropertyManager propMan, 
            RaiseArmAndTraverseDefenseCommandGroup auto,
            AutonomousModeSelector autonomousModeSelector) {
        super(autonomousModeSelector);
        
        traverseDefenseHeading = propMan.createPersistentProperty("traverseDefenseHeading", 90.0);
        traverseDefensePower = propMan.createPersistentProperty("traverseDefensePower", 0.75);
        autoArmGoal = propMan.createPersistentProperty("autonomousArmGoal", 30.0);
        traverseMinTime = propMan.createPersistentProperty("traverseDefenseMinTime", 1.0);
        traverseMaxTime = propMan.createPersistentProperty("traverseDefenseMaxTime", 3.0);
        
        
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
    }
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
