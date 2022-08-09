package main;

import main.metier.Tondeuse;

import java.io.IOException;
import java.net.URISyntaxException;

public class Run {

    public static void main(String[] args) throws IOException, URISyntaxException {

        Tondeuse tondeuse = new Tondeuse("resources/instructions.txt");
        tondeuse.bouger();

    }
}
