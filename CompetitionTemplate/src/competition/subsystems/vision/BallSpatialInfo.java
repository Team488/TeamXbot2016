package competition.subsystems.vision;

public class BallSpatialInfo {
    public float relativeHeading;
    public float distanceInches;
    
    public BallSpatialInfo(float heading, float distanceInches) {
        this.relativeHeading = heading;
        this.distanceInches = distanceInches;
    }
}
