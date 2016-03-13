package competition.subsystems.vision;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import competition.BaseRobotTest;

public class BallConfidenceTrackerTest extends BaseRobotTest {
    private static final double confidenceErrorMargin = 0.0001;
    
    @Test
    public void testEmptyBehavior() {
        BallConfidenceTracker tracker = new BallConfidenceTracker();
        
        assertEquals(0, tracker.getTrackedBalls().length);
        assertEquals(0, tracker.getMaxConfidence(), confidenceErrorMargin);
    }
    
    @Test
    public void testBallsPrunedWhenDisappear() {
        BallConfidenceTracker tracker = new BallConfidenceTracker();
        
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 50, 0));
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(0.3, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);

        processNewBallInfoWithDefaults(tracker);
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(0.2, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);

        processNewBallInfoWithDefaults(tracker);
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(0.1, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);
        

        processNewBallInfoWithDefaults(tracker);
        assertEquals(0, tracker.getTrackedBalls().length);
    }
    
    @Test
    public void testBallIncreaseConfidence() {
        BallConfidenceTracker tracker = new BallConfidenceTracker();
        
        // Add a ball
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 50, 0));
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(0.3, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);

        // Reinforce the ball once and make sure the confidence increases
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 50, 0));
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(0.4, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);
        
        // Reinforce 5 more times
        for(int i = 0; i < 5; i++) {
            processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 50, 0));
        }
        
        // Make sure that the confidence has continued + continues to increment
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(0.9, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);

        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 50, 0));
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(1, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);

        // Make sure that the confidence doesn't go above 1
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 50, 0));
        assertEquals(1, tracker.getTrackedBalls().length);
        assertEquals(1, tracker.getTrackedBalls()[0].getTemporalConfidence(), confidenceErrorMargin);
    }
    
    @Test
    public void testBallMerge() {
        BallConfidenceTracker tracker = new BallConfidenceTracker();
        
        // Add multiple balls within merge distance
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 51, 0), new BallSpatialInfo(0, 50, 0), new BallSpatialInfo(0, 52, 0));
        assertEquals(3, tracker.getTrackedBalls().length);
        assertNotNull(getBallAtDistanceFromTracker(tracker, 50));
        assertEquals(0.3, getBallAtDistanceFromTracker(tracker, 50).getTemporalConfidence(), confidenceErrorMargin);
        
        // Report a single ball within merge range of the first 3 and assert that there is now a ball with the new distance
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 55, 0));
        assertEquals(3, tracker.getTrackedBalls().length);
        assertNotNull(getBallAtDistanceFromTracker(tracker, 55));
        assertEquals(0.4, getBallAtDistanceFromTracker(tracker, 55).getTemporalConfidence(), confidenceErrorMargin);
        
        // Report a ball and make sure that the others haven't been pruned yet
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 56, 0));
        assertEquals(3, tracker.getTrackedBalls().length);
        
        // Report a single ball exists and assert that the others are now pruned
        processNewBallInfoWithDefaults(tracker, new BallSpatialInfo(0, 53, 0));
        assertEquals(1, tracker.getTrackedBalls().length);
        
        // Make sure that the final ball inherited the most recent position
        assertNotNull(getBallAtDistanceFromTracker(tracker, 53));
        assertEquals(0.6, getBallAtDistanceFromTracker(tracker, 53).getTemporalConfidence(), confidenceErrorMargin);
    }
    
    protected void processNewBallInfoWithDefaults(BallConfidenceTracker tracker, BallSpatialInfo ...newBalls) {
        if(newBalls == null) {
            newBalls = new BallSpatialInfo[0];
        }
        
        tracker.processNewBallInfo(Arrays.stream(newBalls).collect(Collectors.toList()), 10, 10, 0.1, -0.1, 0.3);
    }
    
    protected BallSpatialTemporalInfo getBallAtDistanceFromTracker(BallConfidenceTracker tracker, float targetDistance) {
        Optional<BallSpatialTemporalInfo> first = Arrays.stream(tracker.getTrackedBalls())
                .filter(ball -> Math.abs(ball.distanceInches - targetDistance) < 0.0001)
                .findFirst();
        
        return first.isPresent() ? first.get() : null;
    }
}
