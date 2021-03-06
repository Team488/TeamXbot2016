package competition.subsystems.vision;

import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

public class InertJetsonServer implements JetsonServer {
    static Logger log = Logger.getLogger(InertJetsonServer.class);
    
    private Consumer<JetsonCommPacket> packetHandler;
    private boolean healthState = false;

    @Inject
    public InertJetsonServer() {
        
    }
    
    public void setPacketHandler(Consumer<JetsonCommPacket> packetHandler) {
        this.packetHandler = packetHandler;
    }

    public void startServer() {
        
    }

    public void stopServer() {
        
    }
    
    public void simulateNewPacket(JetsonCommPacket newPacket) {
        packetHandler.accept(newPacket);
    }
    
    public void setHealthState(boolean newState) {
        this.healthState = newState;
    }

    @Override
    public boolean isConnectionHealthy() {
        return healthState;
    }
}