import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        assertEquals(2, value);
        assertEquals(1, cache.getSize());
    }

    @Test
    public void getNullTest() {
        assertNull(cache.get(2));
        assertEquals(0, cache.getSize());
    }

    @Test
    public void sizeNotGreaterThenCapacity() {
        for (int i = 0; i <= DEFAULT_CAPACITY; ++i) {
            cache.put(i, i);
        }
        assertEquals(10, cache.getSize());
    }

    @Test
    public void lastUsedElemDeletedTest() {
        for (int i = 0; i <= DEFAULT_CAPACITY; ++i) {
            cache.put(i, i);
        }
        assertNull(cache.get(0));
        for (int i = 1; i <= DEFAULT_CAPACITY; ++i) {
            assertEquals(i, cache.get(i));
        }
        assertEquals(DEFAULT_CAPACITY, cache.getHottestValue());
        assertEquals(DEFAULT_CAPACITY, cache.getHottestKey());
        assertEquals(10, cache.getSize());
    }

    @Test
    public void putSameKeyTest() {
        cache.put(1, 1);
        assertEquals(1, cache.get(1));
        assertEquals(1, cache.getSize());
        cache.put(1, 2);
        assertEquals(2, cache.get(1));
        assertEquals(1, cache.getSize());
    }

    @Test
    public void capacityOneTest() {
        createLRUCache(1);
        cache.put(1, 1);
        cache.put(2, 2);
        assertEquals(1, cache.getSize());
        assertEquals(2, cache.get(2));
        assertNull(cache.get(1));
    }

    @Test
    public void getNotLastAddedElemTest() {
        for (int i = 0; i < DEFAULT_CAPACITY; ++i) {
            cache.put(i, i);
        }
        // gotten element should be in head
        cache.get(4);
        assertEquals(4, cache.getHottestKey());
        assertEquals(4, cache.getHottestValue());
    }
}
