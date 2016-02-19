package competition.subsystems.vision;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import edu.wpi.first.wpilibj.Timer;

public class NetworkedJetsonServer extends Thread implements JetsonServer {
    static Logger log = Logger.getLogger(NetworkedJetsonServer.class);
    
    private final int connectionPort = 3000;
    private final double healthyTimeThreshold = 0.5; // Seconds
    
    private volatile boolean isRunning = false;
    private volatile DatagramSocket serverSocket;
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
            serverSocket = new DatagramSocket(this.connectionPort);
            this.isRunning = true;
            this.start();
        } catch (IOException e) {
            log.error("Jetson server failed to start!");
            log.error(e.toString());
        }
    }

    public void stopServer() {
        this.isRunning = false;
        serverSocket.close();
    }

    @Override
    public void run() {
        log.debug("Jetson server thread starting");
        
        while(isRunning) {
            try {            
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);
    
                int[] intReceiveBuffer = convertBytesToInts(receiveBuffer);
                
                JetsonCommPacket currentPacket = new JetsonCommPacket(intReceiveBuffer);
                currentPacket.setReciptTimestamp(Timer.getFPGATimestamp());
                
                log.debug("Read packet. Calling handler.");
    
                lastPacket = currentPacket;
                packetHandler.accept(currentPacket);
    
            } catch (EOFException e) {
                log.error("Vision client closed connection unexpectedly!"
                        + " This may mean that it misreported packet length.");
            }
            catch (IOException e) {
                log.error("Exception thrown in Jetson packet read loop!");
                log.error(e.toString());
            }
        }
    }

    private int[] convertBytesToInts(byte[] inputBytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(inputBytes);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        
        int[] receiveIntBuffer = new int[intBuffer.remaining()];
        intBuffer.get(receiveIntBuffer);
        
        return receiveIntBuffer;
    }

    @Override
    public boolean isConnectionHealthy() {
        return Timer.getFPGATimestamp() - lastPacket.getRecieptTimestamp() <= healthyTimeThreshold;
    }
}