package xyz.theprogramsrc.supercoreapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecallTest {

    @Test
    void recallWithString() {
        Recall<String> recall = Assertions::assertNotNull;

        recall.run("This is a test!");
    }
}