package competition.subsystems.vision;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BallConfidenceTracker {
    protected List<BallSpatialTemporalInfo> currentBalls;
    
    public BallConfidenceTracker() {
        this.currentBalls = new ArrayList<>();
    }
    
    public void processNewBallInfo(
            List<BallSpatialInfo> newBalls,
            double maxHeadingCorrelationDifference,
            double maxDistanceCorrelationDifference,
            int confidenceIncrement,
            int confidenceDecrement,
            int initialConfidence) {
        
        Set<BallSpatialInfo> uncorrelatedBalls = new HashSet<>(newBalls);
        Set<BallSpatialTemporalInfo> ballsToPrune = new HashSet<>();
        
        for(BallSpatialTemporalInfo existingBall : currentBalls) {
            Set<BallSpatialInfo> ballsInRange = new HashSet<>();
            
            BallSpatialInfo closestBall = null;
            double closestHeadingDiff = 0;
            
            for(BallSpatialInfo uncorrelatedBall : uncorrelatedBalls) {
                double headingDiff = Math.abs(uncorrelatedBall.relativeHeading - existingBall.relativeHeading);
                double distDiff = Math.abs(uncorrelatedBall.distanceInches - existingBall.distanceInches);
                
                if(headingDiff < maxHeadingCorrelationDifference && distDiff < maxDistanceCorrelationDifference) {
                    ballsInRange.add(uncorrelatedBall);
                    
                    if(closestBall == null || headingDiff < closestHeadingDiff) {
                        closestBall = uncorrelatedBall;
                        closestHeadingDiff = headingDiff;
                    }
                }
            }
            
            uncorrelatedBalls.removeAll(ballsInRange);
            
            if(ballsInRange.isEmpty()) {
                existingBall.adjustTemporalConfidence(confidenceDecrement);
            }
            else {
                existingBall.copySpatialInfoFrom(closestBall);
                existingBall.adjustTemporalConfidence(confidenceIncrement);
            }
            
            if (existingBall.getTemporalConfidence() < 1) {
                ballsToPrune.add(existingBall);
            }
        }
        
        currentBalls.removeAll(ballsToPrune);
        
        for(BallSpatialInfo newBall : uncorrelatedBalls) {
            BallSpatialTemporalInfo newTemporalBall = new BallSpatialTemporalInfo(newBall, initialConfidence);
            
            currentBalls.add(newTemporalBall);
        }
        
        currentBalls.sort(Comparator.comparingInt(ball -> -ball.getTemporalConfidence()));
    }
    
    public void resetTrackedBalls() {
        this.currentBalls.clear();
    }
    
    public BallSpatialTemporalInfo[] getTrackedBalls() {
        return this.currentBalls.toArray(new BallSpatialTemporalInfo[currentBalls.size()]);
    }
    
    public int getMaxConfidence() {
        int maxConfidence = 0;
        
        for(BallSpatialTemporalInfo ball : currentBalls) {
            maxConfidence = Math.max(maxConfidence, ball.getTemporalConfidence());
        }
        
        return maxConfidence;
    }
}
