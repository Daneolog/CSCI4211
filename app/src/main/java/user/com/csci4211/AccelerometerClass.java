package user.com.csci4211;

/**
 * Created by user on 12/15/16.
 */
public class AccelerometerClass {
    float x, y, z, timestamp, accuracy;

    public AccelerometerClass(float x, float y, float z, float timestamp, float accuracy) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;
        this.accuracy = accuracy;
    }
}
