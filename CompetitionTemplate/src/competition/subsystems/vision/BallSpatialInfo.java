package competition.subsystems.vision;

public class BallSpatialInfo {
    public float relativeHeading;
    public float distanceInches;
    public float confidence;
    
    public BallSpatialInfo(float heading, float distanceInches, float confidence) {
        this.relativeHeading = heading;
        this.distanceInches = distanceInches;
        this.confidence = confidence;
    }
}
