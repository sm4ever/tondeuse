package test;

import main.metier.Tondeuse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;


class TondeuseTest {
    Tondeuse tondeuse;
    @BeforeEach
    void setUp() throws IOException, URISyntaxException {
         tondeuse = new Tondeuse("resources/instructions.txt");
    }

    @Test
    void testBouger() {
        String result = tondeuse.bouger();
        Assertions.assertEquals("5 1 E", result);
    }

}