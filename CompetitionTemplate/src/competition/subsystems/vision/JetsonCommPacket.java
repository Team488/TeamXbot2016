package competition.subsystems.vision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class JetsonCommPacket {
    private static final int startOfPacketByte = Integer.MAX_VALUE;

    private PacketParserState currentParserState = PacketParserState.WAITING_FOR_START;

    private ArrayList<Integer> packetPayloadData = new ArrayList<>();
    private int expectedPayloadLength = -1;
    private PacketPayloadType payloadType = PacketPayloadType.UNKNOWN;

    public JetsonCommPacket() {

    }

    public boolean addNewValue(int newValue) {
        switch (currentParserState) {
            case WAITING_FOR_START:
                // Skip data until we find the start of a packet
                if (newValue == startOfPacketByte) {
                    this.currentParserState = PacketParserState.WAITING_FOR_PAYLOAD_TYPE_FLAG;
                }
                break;

            case WAITING_FOR_PAYLOAD_TYPE_FLAG:
                this.payloadType = PacketPayloadType.parse(newValue);
                currentParserState = PacketParserState.WAITING_FOR_PAYLOAD_LENGTH;
                break;

            case WAITING_FOR_PAYLOAD_LENGTH:
                this.expectedPayloadLength = newValue;
                currentParserState = PacketParserState.WAITING_FOR_PAYLOAD_DATA;
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
            default:
                // Do nothing
        }
        // TODO: Decide if we want to consume one more byte to confirm that we hit the end

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("JetsonCommPacket (");
        builder.append(this.currentParserState.toString());
        builder.append(", ");
        builder.append(this.payloadType);
        builder.append(") ");

        builder.append(" {");
        for (int i : this.packetPayloadData) {
            builder.append(i);
            builder.append(", ");
        }
        builder.append("}");

        return builder.toString();
    }

    public PacketPayloadType getPayloadType() {
        return this.payloadType;
    }

    public PacketParserState getCurrentParserState() {
        return this.currentParserState;
    }

    public int[] getPayloadData() {
        return convertIntegers(this.packetPayloadData);
    }

    private static int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        
        return ret;
    }

    public enum PacketParserState {
        WAITING_FOR_START, WAITING_FOR_PAYLOAD_TYPE_FLAG, WAITING_FOR_PAYLOAD_LENGTH, WAITING_FOR_PAYLOAD_DATA, PARSE_COMPLETE
    }

    public enum PacketPayloadType {
        UNKNOWN, BALL_RECT_ARRAY;

        private static HashMap<Integer, PacketPayloadType> payloadLookup = new HashMap<Integer, PacketPayloadType>() {
            {
                put(0, PacketPayloadType.UNKNOWN);
                put(1, PacketPayloadType.BALL_RECT_ARRAY);
            }
        };

        public static PacketPayloadType parse(int value) {
            return payloadLookup.get(value);
        }
    }
}
