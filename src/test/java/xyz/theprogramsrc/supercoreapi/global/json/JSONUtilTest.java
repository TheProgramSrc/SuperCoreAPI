package xyz.theprogramsrc.supercoreapi.global.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONUtilTest {

    private JSONUtil jsonUtil;
    
    @BeforeEach
    void setUp() {
        this.jsonUtil = new JSONUtil();
        this.jsonUtil.add("boolean", true);
        this.jsonUtil.add("string", "Text");
        this.jsonUtil.add("number", 1);
    }

    @Test
    void BooleanTest() {
        assertTrue(this.jsonUtil.getBoolean("boolean"));
    }

    @Test
    void StringTest() {
        assertNotNull(this.jsonUtil.getString("string"));
        assertEquals("Text", this.jsonUtil.getString("string"));
    }

    @Test
    void NumberTest() {
        assertEquals(1, this.jsonUtil.getInt("number"));
        assertEquals(2, this.jsonUtil.incr("number"));
        assertEquals(1, this.jsonUtil.decr("number"));
    }

    @Test
    void NullTest() {
        boolean result = false;
        try{
            assertNull(this.jsonUtil.getString("non_existent"));
        }catch (NullPointerException e){
            result = true;
        }

        assertTrue(result);
    }
}