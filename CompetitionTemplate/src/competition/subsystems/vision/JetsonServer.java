package competition.subsystems.vision;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

public class JetsonServer extends Thread {
    static Logger log = Logger.getLogger(JetsonServer.class);
    
    private int connectionPort;
    private volatile boolean isRunning = false;
    private volatile ServerSocket serverSocket;
    private Consumer<JetsonCommPacket> packetHandler;

    public JetsonServer(int connectionPort, Consumer<JetsonCommPacket> packetHandler) {
        this.connectionPort = connectionPort;
        this.packetHandler = packetHandler;
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(this.connectionPort);
        this.start();
    }

    public void stopServer() throws IOException {
        this.isRunning = false;
        serverSocket.close();
    }

    @Override
    public void run() {
        log.debug("Jetson server thread starting");
        
        this.isRunning = true;
        while (isRunning) {
            try {
                log.debug("Waiting for connection...");

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
}