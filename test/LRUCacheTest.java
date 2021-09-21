import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {
    private LRUCache<Integer, Integer> cache;
    private final static int DEFAULT_CAPACITY = 10;

    @BeforeEach
    public void beforeEach() {
        createLRUCache(DEFAULT_CAPACITY);
    }

    public void createLRUCache(int capacity) {
        cache = new LRUCache<>(capacity);
    }

    @Test
    public void simpleTest() {
        cache.put(2, 2);
        int value = cache.get(2);
        assertEquals(value, 2);
        assertEquals(cache.getSize(), 1);
    }

    @Test
    public void getNullTest() {
        assertNull(cache.get(2));
        assertEquals(cache.getSize(), 0);
    }

    @Test
    public void sizeNotGreaterThenCapacity() {
        for (int i = 0; i <= DEFAULT_CAPACITY; ++i) {
            cache.put(i, i);
        }
        assertEquals(cache.getSize(), 10);
    }

    @Test
    public void lastUsedElemDeletedTest() {
        for (int i = 0; i <= DEFAULT_CAPACITY; ++i) {
            cache.put(i, i);
        }
        assertNull(cache.get(0));
        for (int i = 1; i <= DEFAULT_CAPACITY; ++i) {
            assertEquals(cache.get(i), i);
        }
        assertEquals(cache.getSize(), 10);
    }
}
