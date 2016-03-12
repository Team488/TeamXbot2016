package competition.subsystems.vision;

import java.awt.Rectangle;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.XPropertyManager;
import xbot.common.properties.DoubleProperty;

@Singleton
public class VisionSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(VisionSubsystem.class);
    
    private JetsonServerManager jetsonServer;
    protected DoubleProperty maxDistanceProperty;
    
    protected DoubleProperty maxHeadingCorrelationDifference;
    protected DoubleProperty maxDistanceCorrelationDifference;
    
    protected List<BallSpatialTemporalInfo> currentBalls;
    
    @Inject
    public VisionSubsystem(JetsonServerManager jetsonServer, WPIFactory factory, XPropertyManager propMan) {
        log.info("Creating VisionSubsystem");
        this.jetsonServer = jetsonServer;

        maxDistanceProperty = propMan.createPersistentProperty("Max ball collect dist", 96d);
        maxHeadingCorrelationDifference = propMan.createPersistentProperty("Max heading correlation diff", 5);
        maxDistanceCorrelationDifference = propMan.createPersistentProperty("Max dist correlation diff", 24);
    }
    
    public BallSpatialInfo[] getBoulderInfo() {
        return jetsonServer.getLastSpatialInfoArray();
    }
    
    public BallSpatialInfo findTargetBall() {
        BallSpatialInfo[] ballInfo = this.getBoulderInfo();
        
        if(ballInfo == null) {
            return null;
        }
        
        BallSpatialInfo targetBall = null;
        for(BallSpatialInfo ball : ballInfo) {
            if(targetBall == null || (ball.colorConfidence > targetBall.colorConfidence && ball.distanceInches <= getMaxBallAcquireDistance())) {
                targetBall = ball;
            }
        }
        
        return targetBall;
    }
    
    public boolean isConnectionHealthy() {
        return jetsonServer.isConnectionHealthy();
    }

    public boolean isTrackingAnyBalls() {
        BallSpatialInfo[] ballInfo = this.getBoulderInfo();
        return ballInfo == null || ballInfo.length <= 0;
    }
    
    public double getMaxBallAcquireDistance() {
        return maxDistanceProperty.get();
    }
}