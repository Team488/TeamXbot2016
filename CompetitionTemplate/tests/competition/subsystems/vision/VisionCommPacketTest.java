package competition.subsystems.vision;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import competition.BaseRobotTest;
import competition.subsystems.shifting.ShiftingSubsystem;
import competition.subsystems.shifting.commands.ShiftHighCommand;
import competition.subsystems.shifting.commands.ShiftLowCommand;
import competition.subsystems.vision.JetsonCommPacket.PacketParserState;
import competition.subsystems.vision.JetsonCommPacket.PacketPayloadType;
import xbot.common.command.BaseRobot;
import xbot.common.injection.BaseWPITest;

public class VisionCommPacketTest extends BaseRobotTest {
    
    @Before
    public void setup() {
        
    }
    
    @Test
    public void testParseStandardPacket() {
        JetsonCommPacket testPacket = new JetsonCommPacket();
        
        assertEquals(PacketParserState.WAITING_FOR_START, testPacket.getCurrentParserState());
        
        assertTrue(testPacket.addNewValue(Integer.MAX_VALUE));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_TYPE_FLAG, testPacket.getCurrentParserState());

        assertTrue(testPacket.addNewValue(-1));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_LENGTH, testPacket.getCurrentParserState());
        assertEquals(PacketPayloadType.RAW_TEST_DATA, testPacket.getPayloadType());
        
        assertTrue(testPacket.addNewValue(5));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_DATA, testPacket.getCurrentParserState());

        assertTrue(testPacket.addNewValue('H'));
        assertFalse(testPacket.isParseComplete());
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_DATA, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H' }, testPacket.getPayloadData());
        
        assertTrue(testPacket.addNewValue('e'));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_DATA, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e' }, testPacket.getPayloadData());
        
        assertTrue(testPacket.addNewValue('l'));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_DATA, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e', 'l' }, testPacket.getPayloadData());
        
        assertTrue(testPacket.addNewValue('l'));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_DATA, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e', 'l', 'l' }, testPacket.getPayloadData());

        assertFalse(testPacket.addNewValue('o'));
        assertTrue(testPacket.isParseComplete());
        assertEquals(PacketParserState.PARSE_COMPLETE, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e', 'l', 'l', 'o' }, testPacket.getPayloadData());
        
        
        
        assertFalse(testPacket.addNewValue('?'));
        assertEquals(PacketParserState.PARSE_COMPLETE, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e', 'l', 'l', 'o' }, testPacket.getPayloadData());
    }
    
    @Test
    public void testParseFromArray() {
        JetsonCommPacket testPacket = new JetsonCommPacket();
        
        assertEquals(PacketParserState.WAITING_FOR_START, testPacket.getCurrentParserState());
        
        assertTrue(testPacket.addNewValues(new int[] { Integer.MAX_VALUE, -1, 5, 'H', 'e' }));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_DATA, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e' }, testPacket.getPayloadData());
        
        assertFalse(testPacket.addNewValues(new int[] { 'l', 'l', 'o' }));
        assertTrue(testPacket.isParseComplete());
        assertEquals(PacketParserState.PARSE_COMPLETE, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e', 'l', 'l', 'o' }, testPacket.getPayloadData());
        
        
        
        assertFalse(testPacket.addNewValue('?'));
        assertEquals(PacketParserState.PARSE_COMPLETE, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { 'H', 'e', 'l', 'l', 'o' }, testPacket.getPayloadData());
    }
    
    @Test
    public void testParseBadTypeFlag() {
        JetsonCommPacket testPacket = new JetsonCommPacket();
        
        assertEquals(PacketParserState.WAITING_FOR_START, testPacket.getCurrentParserState());
        
        assertTrue(testPacket.addNewValue(Integer.MAX_VALUE));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_TYPE_FLAG, testPacket.getCurrentParserState());

        assertFalse(testPacket.addNewValue(100));
        assertEquals(PacketParserState.MALFORMED_PACKET_ABORT, testPacket.getCurrentParserState());
        assertEquals(PacketPayloadType.UNKNOWN, testPacket.getPayloadType());
        
        
        assertFalse(testPacket.addNewValue('?'));
        assertEquals(PacketParserState.MALFORMED_PACKET_ABORT, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { }, testPacket.getPayloadData());
    }
    
    @Test
    public void testParseBadLength() {
        JetsonCommPacket testPacket = new JetsonCommPacket();
        
        assertEquals(PacketParserState.WAITING_FOR_START, testPacket.getCurrentParserState());
        
        assertTrue(testPacket.addNewValue(Integer.MAX_VALUE));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_TYPE_FLAG, testPacket.getCurrentParserState());

        assertTrue(testPacket.addNewValue(-1));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_LENGTH, testPacket.getCurrentParserState());
        assertEquals(PacketPayloadType.RAW_TEST_DATA, testPacket.getPayloadType());
        
        assertFalse(testPacket.addNewValue(-10));
        assertTrue(testPacket.isParseComplete());
        assertEquals(PacketParserState.MALFORMED_PACKET_ABORT, testPacket.getCurrentParserState());
        
        
        
        assertFalse(testPacket.addNewValue('?'));
        assertEquals(PacketParserState.MALFORMED_PACKET_ABORT, testPacket.getCurrentParserState());
        assertArrayEquals(new int[] { }, testPacket.getPayloadData());
    }
    
    @Test
    public void testParseEmptyPacket() {
        JetsonCommPacket testPacket = new JetsonCommPacket();
        
        assertEquals(PacketParserState.WAITING_FOR_START, testPacket.getCurrentParserState());
        
        assertTrue(testPacket.addNewValue(Integer.MAX_VALUE));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_TYPE_FLAG, testPacket.getCurrentParserState());

        assertTrue(testPacket.addNewValue(-1));
        assertEquals(PacketParserState.WAITING_FOR_PAYLOAD_LENGTH, testPacket.getCurrentParserState());
        assertEquals(PacketPayloadType.RAW_TEST_DATA, testPacket.getPayloadType());
        
        assertFalse(testPacket.addNewValue(0));
        assertTrue(testPacket.isParseComplete());
        assertEquals(PacketParserState.PARSE_COMPLETE, testPacket.getCurrentParserState());
        
        
        assertArrayEquals(new int[] { }, testPacket.getPayloadData());
    }
}
