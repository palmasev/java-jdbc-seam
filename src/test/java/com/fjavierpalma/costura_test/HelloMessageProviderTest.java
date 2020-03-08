package com.fjavierpalma.costura_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HelloMessageProviderTest {



    private HelloMessageProvider helloMessageProvider;

    @BeforeEach
    void setUp() {
        helloMessageProvider = new HelloMessageProvider();
    }

    @Test
    void ShowSpanishMessage() throws IOException {
        assertEquals("Hola Juan", helloMessageProvider.getMessage("Juan"));
    }

    @Test
    void ShowEnglishMessage() throws IOException {
        helloMessageProvider = new HelloMessageProvider("en");
        assertEquals("Hello Juan", helloMessageProvider.getMessage("Juan"));
    }
}
