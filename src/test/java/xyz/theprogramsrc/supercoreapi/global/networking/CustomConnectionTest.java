package xyz.theprogramsrc.supercoreapi.global.networking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class CustomConnectionTest {

    private CustomConnection customConnection;

    @BeforeEach
    void setUp() throws IOException {
        this.customConnection = new ConnectionBuilder("https://api.theprogramsrc.xyz/connection").connect();
    }

    @Test
    void ContentTest() throws IOException {
        assertEquals("true", this.customConnection.getResponseString());
    }

    @Test
    void ResponseCodeTest() throws IOException{
        assertEquals(200, this.customConnection.getResponseCode());
    }

    @Test
    void URLStringTest() throws URISyntaxException {
        assertEquals("https://api.theprogramsrc.xyz/connection", this.customConnection.getURLString());
    }
}