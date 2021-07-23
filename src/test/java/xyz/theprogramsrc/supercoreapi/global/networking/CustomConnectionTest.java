package xyz.theprogramsrc.supercoreapi.global.networking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

class CustomConnectionTest {

    private CustomConnection customConnection;

    @Test
    void ContentTest() throws IOException {
        if(this.customConnection == null) this.customConnection = ConnectionBuilder.connect("https://raw.githubusercontent.com/TheProgramSrc/PluginsResources/master/SuperCoreAPI/connection-test");
        assertEquals("true", this.customConnection.getResponseString());
    }

    @Test
    void ResponseCodeTest() throws IOException{
        if(this.customConnection == null) this.customConnection = ConnectionBuilder.connect("https://raw.githubusercontent.com/TheProgramSrc/PluginsResources/master/SuperCoreAPI/connection-test");
        assertEquals("200", this.customConnection.getResponseCode());
    }

    @Test
    void URLStringTest() throws IOException, URISyntaxException{
        if(this.customConnection == null) this.customConnection = ConnectionBuilder.connect("https://raw.githubusercontent.com/TheProgramSrc/PluginsResources/master/SuperCoreAPI/connection-test");
        assertEquals("https://raw.githubusercontent.com/TheProgramSrc/PluginsResources/master/SuperCoreAPI/connection-test", this.customConnection.getURLString());
    }
}