package competition.subsystems.drive.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyManager;
import xbot.common.properties.StringProperty;
import competition.subsystems.drive.PoseSubsystem.DefenseState;
import edu.wpi.first.wpilibj.Timer;

@Singleton
public class MonitorDefenseTraversalModule {

    double timeOfRecentPitchEvent;
    DoubleProperty defenseTraversalTime;
    DoubleProperty defenseTraversalPitchThreshold;
    StringProperty defenseTraversalState;
    
    @Inject
    public MonitorDefenseTraversalModule(PropertyManager propMan) {
        timeOfRecentPitchEvent = -99999999;
        defenseTraversalTime = propMan.createPersistentProperty("DefenseTraversalTime", 1.0);
        defenseTraversalPitchThreshold = propMan.createPersistentProperty("DefensePitchLimit", 8.0);
        defenseTraversalState = propMan.createEphemeralProperty("DefenseState", "not initialized");
    }
    
    public DefenseState measureState(double absolutePitch) {
        
        // just in case people don't give absolute pitch
        absolutePitch = Math.abs(absolutePitch);        
        DefenseState state = DefenseState.NotOnDefense;
        
        if (Timer.getFPGATimestamp() - timeOfRecentPitchEvent < defenseTraversalTime.get()) {
            // we tilted recently!
            state = DefenseState.RecentlyOnDefense;
        }
        else if (absolutePitch > defenseTraversalPitchThreshold.get())
        {
            // we're tilting now!
            timeOfRecentPitchEvent = Timer.getFPGATimestamp();
            state = DefenseState.OnDefense;
        }
        else {
            //we're not tilting, and we haven't tilted recently.
            state = DefenseState.NotOnDefense;
        }
        
        defenseTraversalState.set(state.toString());
        return state;        
    }
}
