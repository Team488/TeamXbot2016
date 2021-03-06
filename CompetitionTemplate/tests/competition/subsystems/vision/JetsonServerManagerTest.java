package competition.subsystems.vision;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.BaseRobotTest;

public class JetsonServerManagerTest extends BaseRobotTest {
    
    @Before
    public void setup() {
    }
    
    @Test
    public void testParseSpatialInfo() {
        InertJetsonServer innerServer = new InertJetsonServer();
        JetsonServerManager serverManager = new JetsonServerManager(innerServer);
        
       assertNull(serverManager.getLastSpatialInfoArray());
       
       innerServer.simulateNewPacket(new JetsonCommPacket(new int[] { Integer.MAX_VALUE, 2, 6, 1000, 500, 50, 200, 100, 80 }));
       
       assertEquals(2, serverManager.getLastSpatialInfoArray().length);

       BallSpatialInfo parsedInfoA = serverManager.getLastSpatialInfoArray()[0];
       assertEquals(10, parsedInfoA.relativeHeading, 0.001);
       assertEquals(5, parsedInfoA.distanceInches, 0.001);
       assertEquals(0.5, parsedInfoA.colorConfidence, 0.001);

       BallSpatialInfo parsedInfoB = serverManager.getLastSpatialInfoArray()[1];
       assertEquals(2, parsedInfoB.relativeHeading, 0.001);
       assertEquals(1, parsedInfoB.distanceInches, 0.001);
       assertEquals(0.8, parsedInfoB.colorConfidence, 0.001);
    }
    
    @Test
    public void testCheckConnectionHealth() {
        InertJetsonServer innerServer = new InertJetsonServer();
        JetsonServerManager serverManager = new JetsonServerManager(innerServer);
        
        innerServer.setHealthState(true);
        assertTrue(serverManager.isConnectionHealthy());

        innerServer.setHealthState(false);
        assertFalse(serverManager.isConnectionHealthy());
    }
}
