package competition.subsystems.autonomous.selection;

import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

import com.google.inject.Inject;

import competition.subsystems.drive.commands.TraverseDefenseCommand;


public class SetupTraverseDefenseCommand extends BaseAutonomousModeSetCommand {
    final DoubleProperty traverseDefensePower;
    final DoubleProperty traverseDefenseHeading;
    
    final DoubleProperty traverseMinTime;
    final DoubleProperty traverseMaxTime;
    
    public final TraverseDefenseCommand traverseDefenseCommand;
    
    @Inject
    public SetupTraverseDefenseCommand(XPropertyManager propMan, 
            TraverseDefenseCommand traverseDefenseCommand, AutonomousModeSelector autonomousModeSelector) {
        super(autonomousModeSelector);
        traverseDefenseHeading = propMan.createPersistentProperty("traverseDefenseHeading", -90.0);
        traverseDefensePower = propMan.createPersistentProperty("traverseDefensePower", -0.90);
        traverseMinTime = propMan.createPersistentProperty("traverseMinTime", 1.5);
        traverseMaxTime = propMan.createPersistentProperty("traverseMaxTime", 4);
        
        this.traverseDefenseCommand = traverseDefenseCommand;
    }

    @Override
    public void initialize() {
        traverseDefenseCommand.setTargetHeading(traverseDefenseHeading.get());
        traverseDefenseCommand.setPower(traverseDefensePower.get());
        traverseDefenseCommand.setTimeLimits(traverseMinTime.get(), traverseMaxTime.get());
        this.autonomousModeSelector.setCurrentAutonomousCommand(traverseDefenseCommand);
    }

    
    
    @Override
    public boolean isFinished() {
        return true;
    }
}
