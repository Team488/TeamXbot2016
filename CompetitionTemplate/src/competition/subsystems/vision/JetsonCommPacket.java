package competition.subsystems.vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

/**
 * Parses and stores data from incoming vision packets.
 *
 */
public class JetsonCommPacket {
    static Logger log = Logger.getLogger(JetsonCommPacket.class);
    
    private static final int startOfPacketInt = Integer.MAX_VALUE;

    private PacketParserState currentParserState = PacketParserState.WAITING_FOR_START;

    private ArrayList<Integer> packetPayloadData = new ArrayList<>();
    private int expectedPayloadLength = -1;
    private PacketPayloadType payloadType = PacketPayloadType.UNKNOWN;

    private double reciptTimestamp = Double.NEGATIVE_INFINITY;
    
    public JetsonCommPacket() {

    }

    /*
     * Packets are sets of 32-bit (4-byte) signed integers, formatted as follows.
     * 
     * [0] Packet start flag. Equivalent to Integer.MAX_VALUE.
     * [1] Payload type flag. Integer specifying the type of data, as described in PacketPayloadType.
     * [2] Packet length. Length of packet, indicating the number of 32-bit integers in the payload.
     * [3..packet length + 2] Payload data. Type-dependent.
     */

    /**
     * Processes a newly-received integer value and updates internal state tracking accordingly.
     * @param newValue the new value to process.
     * @return A boolean indicating whether more data are needed to fill this packet.
     */
    public boolean addNewValue(int newValue) {
        switch (currentParserState) {
            case WAITING_FOR_START:
                // Skip data until we find the start of a packet
                if (newValue == startOfPacketInt) {
                    this.currentParserState = PacketParserState.WAITING_FOR_PAYLOAD_TYPE_FLAG;
                }
                break;

            case WAITING_FOR_PAYLOAD_TYPE_FLAG:
                this.payloadType = PacketPayloadType.parse(newValue);
                
                if(this.payloadType == null) {
                    currentParserState = PacketParserState.MALFORMED_PACKET_ABORT;
                    // If we get junky data, give up
                    return false;
                }
                else {
                    currentParserState = PacketParserState.WAITING_FOR_PAYLOAD_LENGTH;
                }
                break;

            case WAITING_FOR_PAYLOAD_LENGTH:
                this.expectedPayloadLength = newValue;
                
                if(this.expectedPayloadLength >= 0) {
                    currentParserState = PacketParserState.WAITING_FOR_PAYLOAD_DATA;
                }
                else {
                    currentParserState = PacketParserState.MALFORMED_PACKET_ABORT;
                    // If we get junky data, give up
                    return false;
                }
                break;

            case WAITING_FOR_PAYLOAD_DATA:
                this.packetPayloadData.add((Integer) newValue);

                if (packetPayloadData.size() >= expectedPayloadLength) {
                    currentParserState = PacketParserState.PARSE_COMPLETE;
                    return false;
                }
                break;
            case PARSE_COMPLETE:
                // We shouldn't get here; consumer should stop after all data is received
                return false;
            case MALFORMED_PACKET_ABORT:
                return false;
            default:
                // Do nothing
        }

        return true;
    }
    
    public boolean addNewValues(int[] newValues) {
        if(newValues == null || newValues.length <= 0) {
            log.error("No values given to add!");
            return !isParseComplete();
        }
        
        boolean shouldContinue = true;
        for(int i = 0;
                i < newValues.length && (shouldContinue = addNewValue(newValues[i]));
                i++) {
            continue;
        }
        
        return shouldContinue;
    }
    
    public boolean isParseComplete() {
        return this.currentParserState == PacketParserState.PARSE_COMPLETE
                || this.currentParserState == PacketParserState.MALFORMED_PACKET_ABORT;
    }

    @Override
    public String toString() {
        String result = "JetsonCommPacket [" + this.currentParserState + ", " + this.payloadType + "]";

        result += " { ";
        for (int i : this.packetPayloadData) {
            result += i + " ";
        }
        result += "}";

        return result;
    }

    public PacketPayloadType getPayloadType() {
        return this.currentParserState == PacketParserState.MALFORMED_PACKET_ABORT ? PacketPayloadType.UNKNOWN
                : this.payloadType;
    }

    public PacketParserState getCurrentParserState() {
        return this.currentParserState;
    }

    public int[] getPayloadData() {
        return this.packetPayloadData.stream().mapToInt(i->i).toArray();
    }
    
    public double getReciptTimestamp() {
        return this.reciptTimestamp;
    }
    
    public void setReciptTimestamp(double value) {
        this.reciptTimestamp = value;
    }

    public enum PacketParserState {
        WAITING_FOR_START,
        WAITING_FOR_PAYLOAD_TYPE_FLAG,
        WAITING_FOR_PAYLOAD_LENGTH,
        WAITING_FOR_PAYLOAD_DATA,
        PARSE_COMPLETE,
        MALFORMED_PACKET_ABORT
    }

    public enum PacketPayloadType {
        UNKNOWN, BALL_RECT_ARRAY, BALL_SPATIAL_INFO;

        private static HashMap<Integer, PacketPayloadType> payloadLookup = new HashMap<Integer, PacketPayloadType>() {
            {
                put(1, PacketPayloadType.BALL_RECT_ARRAY);
                put(2, PacketPayloadType.BALL_SPATIAL_INFO);
            }
        };

        public static PacketPayloadType parse(int value) {
            return payloadLookup.get(value);
        }
    }
}
