package competition.subsystems.vision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.vision.JetsonCommPacket.PacketPayloadType;
import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.WPIFactory;
import xbot.common.properties.XPropertyManager;
import xbot.common.properties.DoubleProperty;

@Singleton
public class VisionSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(VisionSubsystem.class);
    
    private JetsonServerManager jetsonServer;
    private BallConfidenceTracker ballTracker;
    
    protected DoubleProperty maxDistanceProperty;
    
    protected DoubleProperty maxHeadingCorrelationDifference;
    protected DoubleProperty maxDistanceCorrelationDifference;
    
    protected DoubleProperty confidenceIncrement;
    protected DoubleProperty confidenceDecrement;
    protected DoubleProperty initialConfidence;
    
    protected DoubleProperty maxConfidenceProp;
    
    @Inject
    public VisionSubsystem(JetsonServerManager jetsonServer, WPIFactory factory, XPropertyManager propMan) {
        log.info("Creating VisionSubsystem");
        
        this.jetsonServer = jetsonServer;
        this.ballTracker = new BallConfidenceTracker();
        
        jetsonServer.setNewPacketNotification(packetType -> handleNewPacketEvent(packetType));

        maxDistanceProperty = propMan.createPersistentProperty("Max ball collect dist", 96d);
        
        maxHeadingCorrelationDifference = propMan.createPersistentProperty("Max heading correlation diff", 5);
        maxDistanceCorrelationDifference = propMan.createPersistentProperty("Max dist correlation diff", 24);

        confidenceIncrement = propMan.createPersistentProperty("Ball confidence increment", 10);
        confidenceDecrement = propMan.createPersistentProperty("Ball confidence decrement", -10);
        initialConfidence = propMan.createPersistentProperty("Ball initial confidence", 30);

        maxConfidenceProp = propMan.createEphemeralProperty("Maximum ball confidence", 0);
        
    }
    
    private void handleNewPacketEvent(PacketPayloadType packetType) {
        if(packetType == PacketPayloadType.BALL_SPATIAL_INFO) {
            ballTracker.processNewBallInfo(
                        Arrays.asList(jetsonServer.getLastSpatialInfoArray()),
                        maxHeadingCorrelationDifference.get(),
                        maxDistanceCorrelationDifference.get(),
                        (int)confidenceIncrement.get(),
                        (int)confidenceDecrement.get(),
                        (int)initialConfidence.get()
                    );

            // TODO: This might consume a fair amount of processor time
            this.maxConfidenceProp.set(getMaxConfidence());
        }
    }    
    
    public BallSpatialTemporalInfo[] getTrackedBoulders() {
        return this.ballTracker.getTrackedBalls();
    }
    
    public BallSpatialTemporalInfo findTargetBall() {
        // TODO: Update this method for intelligent temporal logic
        BallSpatialTemporalInfo[] ballInfo = this.getTrackedBoulders();
        
        if(ballInfo == null) {
            return null;
        }
        
        BallSpatialTemporalInfo targetBall = null;
        for(BallSpatialTemporalInfo ball : ballInfo) {
            if(targetBall == null
                    || (ball.getTemporalConfidence() > targetBall.getTemporalConfidence()
                            && ball.distanceInches <= getMaxBallAcquireDistance())) {
                targetBall = ball;
            }
        }
        
        return targetBall;
    }
    
    public boolean isConnectionHealthy() {
        return jetsonServer.isConnectionHealthy();
    }

    public int getMaxConfidence() {
        return ballTracker.getMaxConfidence();
    }

    public boolean isTrackingAnyBalls() {
        BallSpatialInfo[] ballInfo = this.getTrackedBoulders();
        return ballInfo == null || ballInfo.length <= 0;
    }
    
    public double getMaxBallAcquireDistance() {
        return maxDistanceProperty.get();
    }
}