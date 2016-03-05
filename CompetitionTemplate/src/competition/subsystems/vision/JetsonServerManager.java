package competition.subsystems.vision;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.vision.JetsonCommPacket.PacketPayloadType;

@Singleton
public class JetsonServerManager {
    static Logger log = Logger.getLogger(JetsonServerManager.class);

    private Rectangle[] lastSentRectArray = null;
    private BallSpatialInfo[] lastSentSpatialInfo = null;
    
    private JetsonServer server;

    @Inject
    public JetsonServerManager(JetsonServer server) {
        this.server = server;
        server.setPacketHandler(packet -> handlePacket(packet));
        
        server.startServer();
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
        log.debug("Vision packet recieved: " + newPacket);
        
        int[] payload = newPacket.getPayloadData();
        
        if (newPacket.getPayloadType() == PacketPayloadType.BALL_RECT_ARRAY) {
            
            this.lastSentRectArray = new Rectangle[0];
            this.lastSentRectArray = parseObjectsFromPayload(payload, 4, data -> {
                return new Rectangle(data[0], data[1], data[2], data[3]);
            }).toArray(lastSentRectArray);
            
        } else if (newPacket.getPayloadType() == PacketPayloadType.BALL_SPATIAL_INFO) {
            
            lastSentSpatialInfo = new BallSpatialInfo[0];
            lastSentSpatialInfo = parseObjectsFromPayload(payload, 3, data -> {
                return new BallSpatialInfo(data[0] / 100f, data[1] / 100f, data[2] / 100f);
            }).toArray(lastSentSpatialInfo);
        } else {
            log.error("Unhandled packet header type!");
        }
    }

    @Deprecated
    public Rectangle[] getLastRectArray() {
        return lastSentRectArray;
    }

    public BallSpatialInfo[] getLastSpatialInfoArray() {
        return lastSentSpatialInfo;
    }
    
    public boolean isConnectionHealthy() {
        return this.server.isConnectionHealthy();
    }
}
