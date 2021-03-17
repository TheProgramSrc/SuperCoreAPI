package xyz.theprogramsrc.supercoreapi.global.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    private final Pair<String, Integer> pair = new Pair<>("test", 1);

    @Test
    void left() {
        assertEquals("test", this.pair.getLeft());
    }

    @Test
    void right() {
        assertEquals(1, this.pair.getRight());
    }
}