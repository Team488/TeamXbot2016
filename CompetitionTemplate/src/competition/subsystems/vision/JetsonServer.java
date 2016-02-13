package competition.subsystems.vision;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj.DriverStation;

public class JetsonServer extends Thread {
    private int connectionPort;
    private boolean isRunning = false;
    private ServerSocket serverSocket;
    private Consumer<JetsonCommPacket> packetHandler;

    public JetsonServer(int connectionPort, Consumer<JetsonCommPacket> packetHandler) {
        this.connectionPort = connectionPort;
        this.packetHandler = packetHandler;
    }

    public void startServer() throws IOException {
        serverSocket = new ServerSocket(this.connectionPort);
        this.start();
    }

    @Override
    public void run() {
        this.isRunning = true;
        while (isRunning) {
            try {
                DriverStation.reportError("Waiting for connection...", false);

                Socket socket = serverSocket.accept();
                DriverStation.reportError("Connected", false);
                DataInputStream in = new DataInputStream(socket.getInputStream());

                while (isRunning && socket.isConnected() && !socket.isClosed()) {
                    JetsonCommPacket currentPacket = new JetsonCommPacket();

                    int newValue;
                    do {
                        newValue = in.readInt();
                    } while (currentPacket.addNewValue(newValue));

                    packetHandler.accept(currentPacket);
                }

            } catch (IOException e) {
                continue;
            }
        }
    }
}