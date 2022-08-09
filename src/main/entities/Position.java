package main.entities;

import main.enums.Orentation;

/**
 * définit une classe position qui regroupe X,Y, orientations et les instructions de mouvement
 */
public class Position {
    private int x;
    private int y;
    private Orentation orentation;
    private char[] steps;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Orentation getOrentation() {
        return orentation;
    }

    public void setOrentation(Orentation orentation) {
        this.orentation = orentation;
    }

    public char[] getSteps() {
        return steps;
    }

    public void setSteps(char[] steps) {
        this.steps = steps;
    }
}
