package xyz.theprogramsrc.supercoreapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RecallTest {

    @Test
    void recallWithString() {
        Recall<String> recall = Assertions::assertNotNull;

        recall.run("This is a test!");
    }
}