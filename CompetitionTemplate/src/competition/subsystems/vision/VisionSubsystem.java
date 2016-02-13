package competition.subsystems.vision;

import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.PropertyManager;

@Singleton
public class VisionSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(VisionSubsystem.class);
    
    private JetsonServerManager jetsonServer;
    
    @Inject
    public VisionSubsystem(JetsonServerManager jetsonServer, WPIFactory factory, PropertyManager propManager) {
        log.info("Creating VisionSubsystem");
        this.jetsonServer = jetsonServer;
    }
    
    public Rectangle[] getBoulderRects() {
        return jetsonServer.getLastBallArray();
    }
}