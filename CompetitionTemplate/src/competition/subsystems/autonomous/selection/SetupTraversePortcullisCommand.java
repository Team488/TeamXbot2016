package competition.subsystems.autonomous.selection;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;
import competition.subsystems.autonomous.TraversePortcullisCommandGroup;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class SetupTraversePortcullisCommand extends BaseAutonomousModeSetCommand{
    public TraversePortcullisCommandGroup traversePortcullis;
    public RaiseArmAndTraverseDefenseCommandGroup raiseArm;
    
    final String label = "Portcullis Auto";
    
    DoubleProperty traversePortcullisPower;
    DoubleProperty traversePortcullisHeading;
    DoubleProperty traversePortcullisMin;
    DoubleProperty traversePortcullisMax;
    
    public SetupTraversePortcullisCommand(AutonomousModeSelector autonomousModeSelector, 
            XPropertyManager propManager, 
            TraversePortcullisCommandGroup traversePortcullis) {
        super(autonomousModeSelector);
        traversePortcullisPower = propManager.createPersistentProperty(label + "powerForTraversingPortcullis", 0.5);
        traversePortcullisMin = propManager.createPersistentProperty(label + "minSecondsForTraversingPortcullis", 8.0);
        traversePortcullisMax = propManager.createPersistentProperty(label + "maxSecondsForTraversingPortcullis", 8.0);
        
        this.traversePortcullis = traversePortcullis;
    }

    @Override
    public void initialize() {
        raiseArm.setTraversalProperties(traversePortcullisPower.get(), 0, traversePortcullisMin.get(), traversePortcullisMax.get());
        
        this.autonomousModeSelector.setCurrentAutonomousCommand(traversePortcullis);   
    }

}
