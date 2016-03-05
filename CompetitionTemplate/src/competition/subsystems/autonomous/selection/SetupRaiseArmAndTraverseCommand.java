package competition.subsystems.autonomous.selection;

import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;
import competition.subsystems.drive.PoseSubsystem;


public abstract class SetupRaiseArmAndTraverseCommand extends BaseAutonomousModeSetCommand {

    public final RaiseArmAndTraverseDefenseCommandGroup auto;
    
    DoubleProperty traverseDefensePower;
    DoubleProperty traverseDefenseHeading;
    DoubleProperty autoArmGoal;
    DoubleProperty traverseMinTime;
    DoubleProperty traverseMaxTime;

    @Inject
    public SetupRaiseArmAndTraverseCommand(XPropertyManager propMan, 
            RaiseArmAndTraverseDefenseCommandGroup auto,
            AutonomousModeSelector autonomousModeSelector) {
        super(autonomousModeSelector);
                
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
