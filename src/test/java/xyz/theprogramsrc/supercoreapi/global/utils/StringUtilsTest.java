package xyz.theprogramsrc.supercoreapi.global.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    private StringUtils stringUtils;

    @BeforeEach
    void setUp() {
        this.stringUtils = new StringUtils("This is just {TestingPlaceholder}! $1");
    }

    @Test
    void lower() {
        StringUtils tmp = this.stringUtils;
        assertEquals("this is just {testingplaceholder}! $1", tmp.lower().get());
    }

    @Test
    void upper() {
        StringUtils tmp = this.stringUtils;
        assertEquals("THIS IS JUST {TESTINGPLACEHOLDER}! $1", tmp.upper().get());
    }

    @Test
    void placeholder() {
        StringUtils tmp = this.stringUtils;
        assertEquals("This is just Testing! $1", tmp.placeholder("{TestingPlaceholder}", "Testing").get());
    }

    @Test
    void var() {
        StringUtils tmp = this.stringUtils;
        assertEquals("This is just {TestingPlaceholder}! test", tmp.vars("test").get());
    }

    @Test
    void capitalize() {
        StringUtils tmp = this.stringUtils;
        assertEquals("This Is Just {TestingPlaceholder}! $1", tmp.capitalize().get());
    }
}