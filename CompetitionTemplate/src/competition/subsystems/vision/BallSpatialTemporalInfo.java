package competition.subsystems.vision;

import xbot.common.math.MathUtils;

public class BallSpatialTemporalInfo extends BallSpatialInfo {
    private int temporalConfidence;
    
    public BallSpatialTemporalInfo(float heading, float distanceInches, float colorConfidence, int initialTemporalConfidence) {
        super(heading, distanceInches, colorConfidence);
        
        this.temporalConfidence = initialTemporalConfidence;
    }
    
    public BallSpatialTemporalInfo(BallSpatialInfo sourceVal, int initialTemporalConfidence) {
        this(sourceVal.relativeHeading, sourceVal.distanceInches, sourceVal.distanceInches, initialTemporalConfidence);
    }
    
    public int getTemporalConfidence() {
        return temporalConfidence;
    }
    
    public void adjustTemporalConfidence(int shift) {
        temporalConfidence += shift;
        constrainTemporalConfidence();
    }
    
    private void constrainTemporalConfidence() {
        temporalConfidence = MathUtils.constrainInt(temporalConfidence, 0, 100);
    }
}
