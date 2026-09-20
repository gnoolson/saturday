package gnoolson.saturday.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class HexFormatTest {

    @Test
    public void general() {
        byte[] bytes = HexFormat.stringToByteArray("c350");
        int anInt = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getShort() & 0xFFFF;
        Assertions.assertEquals(50000, anInt);
    }

    @Test
    public void string_to_bytes_and_back() {
        byte[] bytes = {(byte) 0xA1, (byte) 0xA2, (byte) 0xA3, (byte) 0xA4, (byte) 0xA5, (byte) 0xA6, (byte) 0xA7};
        String str = HexFormat.bytesToString(bytes);
        byte[] result = HexFormat.stringToByteArray(str);

        Assertions.assertArrayEquals(bytes, result);
    }

}