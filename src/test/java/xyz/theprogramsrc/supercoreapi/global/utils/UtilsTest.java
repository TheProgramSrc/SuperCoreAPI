package xyz.theprogramsrc.supercoreapi.global.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void capitalizeTest() {
        String capitalized = Utils.capitalize("abCde");
        assertEquals("AbCde", capitalized);
    }

    @Test
    void toTicksTest() {
        long ticks = Utils.toTicks(5);
        assertEquals(100, ticks);
    }

    @Test
    void toMillisTest() {
        long millis = Utils.toMillis(5);
        assertEquals(5000, millis);
    }

    @Test
    void isIntegerTest() {
        boolean integer = Utils.isInteger("1");
        assertTrue(integer);
    }

    @Test
    void parseHypotenuseTest() {
        double hypotenuse = Utils.parseHypotenuse(3,4);
        assertEquals(25, hypotenuse);
    }

    @Test
    void squareRootTest() {
        double sqrt = Utils.squareRoot(Utils.parseHypotenuse(3,4));
        assertEquals(5, sqrt);
    }

    @Test
    void checksumGeneratorTest() throws IOException, NoSuchAlgorithmException {
        // First we download the sample jar
        File tmpFile = new File("4.13.0.pom.xml");
        Utils.downloadFile("https://repo.theprogramsrc.xyz/repository/maven-public/xyz/theprogramsrc/SuperCoreAPI/4.13.0/SuperCoreAPI-4.13.0.pom", tmpFile);
        String knownHash = "f70c0d83eab4bb30e8794f929299fd9a"; // We Store the known hash
        MessageDigest messageDigest = Utils.getDigest("MD5"); // We generate the message digest
        String generatedHash = Utils.generateFileChecksum(messageDigest, tmpFile); // Now we generate the hash
        assertEquals(knownHash, generatedHash); // Now we check the hash
        tmpFile.deleteOnExit();
    }

    @Test
    void getTimeSecondsFromStringTest(){
        long seconds = Utils.getTimeSecondsFromString("1h 30s");
        assertEquals(3630l, seconds);
    }

    @Test
    void getTimeSecondsFromWordTest(){
        long seconds = Utils.getTimeSecondsFromWord("1h");
        assertEquals(3600l, seconds);
    }
}