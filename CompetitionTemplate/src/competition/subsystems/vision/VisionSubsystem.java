package competition.subsystems.vision;

import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class VisionSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(VisionSubsystem.class);
    
    private JetsonServerManager jetsonServer;
    
    @Inject
    public VisionSubsystem(JetsonServerManager jetsonServer, WPIFactory factory, XPropertyManager propManager) {
        log.info("Creating VisionSubsystem");
        this.jetsonServer = jetsonServer;
    }
    
    @Deprecated
    public Rectangle[] getBoulderRects() {
        return jetsonServer.getLastRectArray();
    }
    
    public BallSpatialInfo[] getBoulderInfo() {
        return jetsonServer.getLastSpatialInfoArray();
    }
    
    public BallSpatialInfo findTargetBall() {
        BallSpatialInfo[] ballInfo = this.getBoulderInfo();
        
        if(ballInfo == null)
            return null;
        
        BallSpatialInfo targetBall = null;
        for(BallSpatialInfo ball : ballInfo) {
            if(targetBall == null || ball.distanceInches < targetBall.distanceInches) {
                targetBall = ball;
            }
        }
        
        return targetBall;
    }
    
    public boolean isConnectionHealthy() {
        return jetsonServer.isConnectionHealthy();
    }

    public boolean trackingAnyBalls() {
        BallSpatialInfo[] ballInfo = this.getBoulderInfo();
        return ballInfo == null || ballInfo.length <= 0;
    }
}