package competition.subsystems.vision;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import edu.wpi.first.wpilibj.Timer;

public class NetworkedJetsonServer extends Thread implements JetsonServer {
    static Logger log = Logger.getLogger(NetworkedJetsonServer.class);
    
    private final int connectionPort = 3000;
    private final double healthyTimeThreshold = 0.5; // Seconds
    
    private volatile boolean isRunning = false;
    private volatile ServerSocket serverSocket;
    private Consumer<JetsonCommPacket> packetHandler;
    
    private JetsonCommPacket lastPacket = null;

    @Inject
    public NetworkedJetsonServer() {
        
    }
    
    public void setPacketHandler(Consumer<JetsonCommPacket> packetHandler) {
        this.packetHandler = packetHandler;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(this.connectionPort);
            this.start();
        } catch (IOException e) {
            log.error("Jetson server failed to start!");
            log.error(e.toString());
        }
    }

    public void stopServer() {
        try {
            this.isRunning = false;
            serverSocket.close();
        } catch (IOException e) {
            log.error("Jetson server failed to stop!");
            log.error(e.toString());
        }
    }

    @Override
    public void run() {
        log.debug("Jetson server thread starting");
        
        this.isRunning = true;
        while (isRunning) {
            try {
                log.info("Waiting for connection...");

                Socket socket = serverSocket.accept();
                log.info("Connected to client");
                
                DataInputStream in = new DataInputStream(socket.getInputStream());

                while (isRunning && socket.isConnected() && !socket.isClosed()) {
                    JetsonCommPacket currentPacket = new JetsonCommPacket();

                    int newValue;
                    do {
                        newValue = in.readInt();
                        log.debug("Reading int " + newValue + "(" + Integer.toUnsignedLong(newValue) + ")");
                    } while (currentPacket.addNewValue(newValue));
                    
                    currentPacket.setReciptTimestamp(Timer.getFPGATimestamp());
                    lastPacket = currentPacket;
                    
                    log.debug("Read packet. Calling handler.");
                    packetHandler.accept(currentPacket);
                }

            } catch (EOFException e) {
                log.error("Vision client closed connection unexpectedly!"
                        + " This may mean that it misreported packet length.");
            }
            catch (IOException e) {
                log.error("Exception thrown in Jetson thread!");
                log.error(e.toString());
            }
        }
    }

    @Override
    public boolean isConnectionHealthy() {
        return lastPacket.getReciptTimestamp() - Timer.getFPGATimestamp() <= healthyTimeThreshold;
    }
}