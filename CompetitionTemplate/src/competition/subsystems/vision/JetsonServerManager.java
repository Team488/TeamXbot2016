package competition.subsystems.vision;

import java.awt.Rectangle;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.inject.Singleton;

import competition.subsystems.vision.JetsonCommPacket.PacketPayloadType;

@Singleton
public class JetsonServerManager {
    static Logger log = Logger.getLogger(JetsonServerManager.class);
    private final int serverPort = 3000;

    private JetsonServer server;

    private Rectangle[] lastSentBallArray = null;

    public JetsonServerManager() {
        server = new JetsonServer(serverPort, packet -> handlePacket(packet));
        try {
            server.startServer();
        } catch (IOException e) {
            log.error("Jetson server faile to start!");
            log.error(e.toString());
        }
    }

    private void handlePacket(JetsonCommPacket newPacket) {
        int[] payload = newPacket.getPayloadData();
        log.debug("Vision packet recieved with payload byte length " + payload.length);
        
        if (newPacket.getPayloadType() == PacketPayloadType.BALL_RECT_ARRAY) {
            if (payload.length % 4 != 0) {
                log.error("Supplied packet data does not include a number of bytes evenly parsable into rects!");
            }

            lastSentBallArray = new Rectangle[payload.length / 4];
            for (int rectIndex = 0; rectIndex < lastSentBallArray.length; rectIndex++) {
                int dataIndex = rectIndex * 4;
                lastSentBallArray[rectIndex] = new Rectangle(payload[dataIndex], payload[dataIndex + 1],
                        payload[dataIndex + 2], payload[dataIndex + 3]);
            }
        }
        else {
            log.error("Unhandled packet header type!");
        }
    }
    
    public Rectangle[] getLastBallArray() {
        return lastSentBallArray;
    }
}
