package competition.subsystems.vision;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class VisionSubsystem extends BaseSubsystem {
    
    private static Logger log = Logger.getLogger(VisionSubsystem.class);
    
    @Inject
    public VisionSubsystem(WPIFactory factory, XPropertyManager propManager) {
        log.info("Creating VisionSubsystem");
    }
    
    public boolean isBoulderInSight()
    {
        return false;
    }
    
    public double getBoulderBearing()
    {
        return 0;
    }
    
    public double getBoulderDistance()
    {
        return 0;
    }
    
    public boolean isGoalInSight()
    {
        return false;
    }
    
    public double getGoalBearing()
    {
        return 0;
    }
    
    public double getGoalHeading()
    {
        return 0;
    }
}