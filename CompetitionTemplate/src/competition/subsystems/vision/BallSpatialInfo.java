package competition.subsystems.vision;

public class BallSpatialInfo {
    public float relativeHeading;
    public float distanceInches;
    public float colorConfidence;
    
    public BallSpatialInfo(float heading, float distanceInches, float colorConfidence) {
        this.relativeHeading = heading;
        this.distanceInches = distanceInches;
        this.colorConfidence = colorConfidence;
    }
}
