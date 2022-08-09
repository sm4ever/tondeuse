package main.metier;

import main.entities.Position;
import main.enums.Mouvement;
import main.enums.Orentation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import java.util.logging.Logger;

public class Tondeuse {
    private static final Logger LOGGER = Logger.getLogger(Tondeuse.class.getName());
    // tailles X,Y max de la grille
    int sizeX;
    int sizeY;
    List<Position> lignes = new ArrayList<>();

    /**
     * constructeur
     * @param filename
     * @throws IOException
     * @throws URISyntaxException
     */
    public Tondeuse(String filename) throws IOException, URISyntaxException {
        readInstructions(filename);
    }

    /**
     * traitement principale
     * @return String resultat de la tondeuse
     */
    public String bouger()   {
        AtomicInteger i = new AtomicInteger(1);
        AtomicReference<String> result = new AtomicReference<>("");
        lignes.forEach(position -> {
            char[] steps = position.getSteps();
            for (char step : steps) {
                avancer(position, step);
            }
            result.set(position.getX() + " " + position.getY() + " " + position.getOrentation());
            LOGGER.info("**** Tondeuse " + i + " FINAL position " + position.getX() + " " + position.getY() + " " + position.getOrentation() + " ****");
            i.getAndIncrement();
        });
        return result.get();
    }

    /**
     * permet de lire un fichier
     * @param filename
     * @throws IOException
     * @throws URISyntaxException
     */
    private void readInstructions(String filename) throws IOException, URISyntaxException {
        Path path = Path.of(ClassLoader.getSystemResource(filename).toURI());
        try (Stream<String> stream = Files.lines(path)) {
            stream.forEach(ligne -> {
                Position position = parseSteps(ligne);
                if (position != null)
                    lignes.add(position);
            });
        }
    }

    /**
     * permet de parser une ligne pour récuperer la taille max de la grille, le point repere et les instructions
     * @param ligne
     * @return position
     */
    private Position parseSteps(String ligne) {
        String[] split = ligne.split(" ");
        if (split.length == 2) {
            sizeX = Integer.parseInt(split[0]);
            sizeY = Integer.parseInt(split[1]);
            return null;
        } else {
            Position position = new Position();
            position.setX(Integer.parseInt(split[0]));
            position.setY(Integer.parseInt(split[1]));

            position.setOrentation(Orentation.valueOf(split[2]));
            position.setSteps(split[3].toCharArray());
            return position;
        }

    }

    /**
     * méthode générale qui controle les mouvements
     * @param position
     * @param step
     * @return
     */
    private Position avancer(Position position, char step) {
        Mouvement mouvement = Mouvement.valueOf(String.valueOf(step));
        if (mouvement == Mouvement.G || mouvement == Mouvement.D)
            position.setOrentation(orienter(mouvement, position));
        if (mouvement == Mouvement.A)
            deplacer(position);
        return position;
    }


    /**
     * deplace la tondeuse dans la grille
     * @param position
     * @return position
     */
    private Position deplacer(Position position) {
        switch (position.getOrentation()) {
            case N:
                if (position.getY() < sizeY)
                    position.setY(position.getY() + 1);
                return position;
            case E:
                if (position.getX() < sizeX)
                    position.setX(position.getX() + 1);
                return position;
            case S:
                if (position.getY() != 0)
                    position.setY(position.getY() - 1);
                return position;
            case W:
                if (position.getX() != 0)
                    position.setX(position.getX() - 1);
                return position;
            default:
                return position;
        }
    }

    /**
     * Oriente la grille NEWS
     * @param mouvement
     * @param position
     * @return Orentation
     */
    private Orentation orienter(Mouvement mouvement, Position position) {
        switch (mouvement) {
            case D:
                return versGauche(position);
            case G:
                return versDroite(position);
            default:
                return Orentation.N;
        }
    }

    /**
     * tourner vers la droite
      * @param position
     * @return Orentation
     */
    private Orentation versDroite(Position position) {
        if (position.getOrentation() == Orentation.N)
            return Orentation.W;
        if (position.getOrentation() == Orentation.E)
            return Orentation.N;
        if (position.getOrentation() == Orentation.S)
            return Orentation.E;
        if (position.getOrentation() == Orentation.W)
            return Orentation.S;
        else return null;
    }

    /**
     * tourner vers la gauche
     * @param position
     * @return Orentation
     */
    private Orentation versGauche(Position position) {
        if (position.getOrentation() == Orentation.N)
            return Orentation.E;
        if (position.getOrentation() == Orentation.E)
            return Orentation.S;
        if (position.getOrentation() == Orentation.S)
            return Orentation.W;
        if (position.getOrentation() == Orentation.W)
            return Orentation.N;
        else return null;
    }

}
