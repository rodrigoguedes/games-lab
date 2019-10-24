package dev.rodrigoguedes.game.zelda.world;

public class Camera {

    private int x = 10;
    private int y = 10;

    public static int clamp(int current, int min, int max) {
        if (current < min) {
            return min;
        }

        if (current > max) {
            return max;
        }

        return current;
    }

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
}
