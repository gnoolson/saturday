package gnoolson.saturday.common.ring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RingArrayImplTest {

    @Test
    public void general() {
        Ring<Integer> ring = new RingArrayImpl<>(5);

        ring.add(1);
        ring.add(2);
        ring.add(3);
        ring.add(4);
        ring.add(5);

        assertEquals(1, (int) ring.poll());
        assertEquals(2, (int) ring.poll());
        assertEquals(3, (int) ring.poll());
        assertEquals(4, (int) ring.poll());
        assertEquals(5, (int) ring.poll());
        assertTrue(ring.isEmpty());

        ring.add(1);
        ring.add(2);
        ring.add(3);
        ring.add(4);
        ring.add(5);
        ring.add(6);

        assertEquals(2, (int) ring.poll());
        assertEquals(3, (int) ring.poll());
        assertEquals(4, (int) ring.poll());
        assertEquals(5, (int) ring.poll());
        assertEquals(6, (int) ring.poll());

        assertTrue(ring.isEmpty());
    }

}