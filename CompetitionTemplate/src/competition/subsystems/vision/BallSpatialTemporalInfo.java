package competition.subsystems.vision;

import xbot.common.math.MathUtils;

public class BallSpatialTemporalInfo extends BallSpatialInfo {
    private double temporalConfidence;
    
    public BallSpatialTemporalInfo(float heading, float distanceInches, float colorConfidence, double initialTemporalConfidence) {
        super(heading, distanceInches, colorConfidence);
        
        this.temporalConfidence = initialTemporalConfidence;
    }
    
    public BallSpatialTemporalInfo(float heading, float distanceInches, float colorConfidence) {
        this(heading, distanceInches, colorConfidence, 0.3);
    }
    
    public BallSpatialTemporalInfo(BallSpatialInfo sourceVal) {
        this(sourceVal.relativeHeading, sourceVal.distanceInches, sourceVal.distanceInches);
    }
    
    public double getTemporalConfidence() {
        return temporalConfidence;
    }
    
    public void adjustTemporalConfidence(double shift) {
        temporalConfidence += shift;
        
        constrainTemporalConfidence();
    }
    
    private void constrainTemporalConfidence() {
        // TODO: Don't use hard-coded magic numbers
        temporalConfidence = MathUtils.constrainDouble(temporalConfidence, 0, 1);
    }
}
