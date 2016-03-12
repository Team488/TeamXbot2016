package competition.subsystems.autonomous.selection;

import competition.subsystems.autonomous.RaiseArmAndTraverseDefenseCommandGroup;
import competition.subsystems.autonomous.TraversePortcullisCommandGroup;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.XPropertyManager;

public class SetupTraversePortcullisCommand extends BaseAutonomousModeSetCommand{
    public TraversePortcullisCommandGroup auto;
    public RaiseArmAndTraverseDefenseCommandGroup raiseArm;
    
    DoubleProperty traversePortcullisPower;
    DoubleProperty traversePortcullisHeading;
    DoubleProperty traversePortcullisMin;
    DoubleProperty traversePortcullisMax;
    
    public SetupTraversePortcullisCommand(AutonomousModeSelector autonomousModeSelector, 
            XPropertyManager propManager, 
            RaiseArmAndTraverseDefenseCommandGroup raiseArm) {
        super(autonomousModeSelector);
        traversePortcullisPower = propManager.createPersistentProperty("powerForTraversingPortcullis", 0.5);
        traversePortcullisHeading= propManager.createPersistentProperty("headingForTraversingPortcullis", 0.0);
        traversePortcullisMin = propManager.createPersistentProperty("minSecondsForTraversingPortcullis", 8.0);
        traversePortcullisMax = propManager.createPersistentProperty("maxSecondsForTraversingPortcullis", 8.0);
        
        this.raiseArm = raiseArm;
    }

    @Override
    public void initialize() {
        raiseArm.setTraversalProperties(traversePortcullisPower.get(), 
                traversePortcullisHeading.get(), 
                traversePortcullisMin.get(), 
                traversePortcullisMax.get());
        
        this.autonomousModeSelector.setCurrentAutonomousCommand(auto);
        
    }

}
