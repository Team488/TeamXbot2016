package competition.subsystems.vision;

import java.util.function.Consumer;

public interface JetsonServer {
    public void startServer();
    public void stopServer();
    
    public void setPacketHandler(Consumer<JetsonCommPacket> packetHandler);
}