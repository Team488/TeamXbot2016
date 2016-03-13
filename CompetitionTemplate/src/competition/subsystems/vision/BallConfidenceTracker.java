package competition.subsystems.vision;

import java.util.ArrayList;
import java.util.Collections;
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
            double confidenceIncrement,
            double confidenceDecrement,
            double initialConfidence) {
        
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
            existingBall.copySpatialInfoFrom(closestBall);
            
            if(ballsInRange.isEmpty()) {
                existingBall.adjustTemporalConfidence(confidenceDecrement);
            }
            else {
                existingBall.adjustTemporalConfidence(confidenceIncrement);
            }
            
            // Comparing doubles, so give it some margin for error
            if (existingBall.getTemporalConfidence() <= 0.00001) {
                ballsToPrune.add(existingBall);
            }
        }
        
        ballsToPrune.removeAll(ballsToPrune);
        
        for(BallSpatialInfo newBall : uncorrelatedBalls) {
            BallSpatialTemporalInfo newTemporalBall = new BallSpatialTemporalInfo(newBall, initialConfidence);
            
            // Keep array in sorted order
            // TODO: Figure out what order this is (we want it to be ascending)
            int searchResult = Collections.binarySearch(currentBalls, newBall, Comparator.comparingDouble(x -> x.distanceInches));
            int targetPos = searchResult >= 0 ? searchResult : -searchResult - 1;
            currentBalls.add(targetPos, newTemporalBall);
        }
    }
    
    public void resetTrackedBalls() {
        this.currentBalls.clear();
    }
    
    public BallSpatialTemporalInfo[] getTrackedBalls() {
        return this.currentBalls.toArray(new BallSpatialTemporalInfo[currentBalls.size()]);
    }
    
    public double getMaxConfidence() {
        double maxConfidence = 0;
        
        for(BallSpatialTemporalInfo ball : currentBalls) {
            maxConfidence = Math.max(maxConfidence, ball.getTemporalConfidence());
        }
        
        return maxConfidence;
    }
}
