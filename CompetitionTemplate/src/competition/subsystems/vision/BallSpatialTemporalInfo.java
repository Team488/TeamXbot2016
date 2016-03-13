package competition.subsystems.vision;

import xbot.common.math.MathUtils;

public class BallSpatialTemporalInfo extends BallSpatialInfo {
    private double temporalConfidence;
    
    public BallSpatialTemporalInfo(float heading, float distanceInches, float colorConfidence, double initialTemporalConfidence) {
        super(heading, distanceInches, colorConfidence);
        
        this.temporalConfidence = initialTemporalConfidence;
    }
    
    public BallSpatialTemporalInfo(BallSpatialInfo sourceVal, double initialTemporalConfidence) {
        this(sourceVal.relativeHeading, sourceVal.distanceInches, sourceVal.distanceInches, initialTemporalConfidence);
    }
    
    public double getTemporalConfidence() {
        return temporalConfidence;
    }
    
    public void adjustTemporalConfidence(double shift) {
        temporalConfidence += shift;
        constrainTemporalConfidence();
    }
    
    private void constrainTemporalConfidence() {
        temporalConfidence = MathUtils.constrainDouble(temporalConfidence, 0, 1);
    }
}
