package competition.subsystems.vision;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import org.apache.log4j.Logger;

import com.google.inject.Singleton;

import competition.subsystems.vision.JetsonCommPacket.PacketPayloadType;

@Singleton
public class JetsonServerManager {
    static Logger log = Logger.getLogger(JetsonServerManager.class);
    private final int serverPort = 3000;

    private JetsonServer server;

    private Rectangle[] lastSentBallArray = null;
    private BallSpatialInfo[] lastSentSpatialInfo = null;

    public JetsonServerManager() {
        server = new JetsonServer(serverPort, packet -> handlePacket(packet));
        try {
            server.startServer();
        } catch (IOException e) {
            log.error("Jetson server faile to start!");
            log.error(e.toString());
        }
    }

    private <T> ArrayList<T> parseObjectsFromPayload(int[] payload, int intsPerObject,
            Function<int[], T> parserFunction) {
        if (payload.length % intsPerObject != 0) {
            log.error("Supplied packet data does not include a number of bytes evenly parsable into given type!");
        }

        ArrayList<T> result = new ArrayList<>(payload.length / intsPerObject);
        for (int index = 0; index < payload.length / intsPerObject; index++) {
            int dataIndex = index * intsPerObject;
            int[] dataSection = Arrays.copyOfRange(payload, dataIndex, dataIndex + intsPerObject);
            result.add(index, parserFunction.apply(dataSection));
        }

        return result;
    }

    private void handlePacket(JetsonCommPacket newPacket) {
        int[] payload = newPacket.getPayloadData();
        log.debug("Vision packet recieved with payload byte length " + payload.length);
log.info("Type: " + newPacket.getPayloadType() + ", Other: " + newPacket.toString());
        if (newPacket.getPayloadType() == PacketPayloadType.BALL_RECT_ARRAY) {
            
            this.lastSentBallArray = new Rectangle[0];
            this.lastSentBallArray = parseObjectsFromPayload(payload, 4, data -> {
                return new Rectangle(data[0], data[1], data[2], data[3]);
            }).toArray(lastSentBallArray);
            
        } else if (newPacket.getPayloadType() == PacketPayloadType.BALL_SPATIAL_INFO) {
            
            lastSentSpatialInfo = new BallSpatialInfo[0];
            lastSentSpatialInfo = parseObjectsFromPayload(payload, 2, data -> {
                log.info("Payload: " + data[0] + ":" + data[1]);
                return new BallSpatialInfo(data[0] / 100f, data[1] / 100f);
            }).toArray(lastSentSpatialInfo);
            
            log.info("Num: " + lastSentSpatialInfo.length);
        } else {
            log.error("Unhandled packet header type!");
        }
    }

    @Deprecated
    public Rectangle[] getLastBallArray() {
        return lastSentBallArray;
    }

    public BallSpatialInfo[] getLastSpatialInfoArray() {
        return lastSentSpatialInfo;
    }
}
