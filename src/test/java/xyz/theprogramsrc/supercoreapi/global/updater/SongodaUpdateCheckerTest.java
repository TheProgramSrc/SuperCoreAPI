package xyz.theprogramsrc.supercoreapi.global.updater;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SongodaUpdateCheckerTest {

    private boolean failed = false;
    private String last = null;

    @BeforeEach
    void setUp() {
        new SongodaUpdateChecker("supercoreapi-the-best-way-to-create-a-plugin") {
            @Override
            public void onFailCheck() {
                SongodaUpdateCheckerTest.this.failed = true;
            }

            @Override
            public void onSuccessCheck(String lastVersion) {
                SongodaUpdateCheckerTest.this.last = lastVersion;
            }
        }.checkUpdates();
    }

    @Test
    void check() {
        assertFalse(this.failed);
    }

    @Test
    void version() {
        assertNotNull(this.last);
    }
}