package com.particlesimulation;

import java.awt.Color;
import java.util.Random;

class Particle {
    private int x, y;
    private final int width, height;
    private final Color color;
    private final Random random;

    public Particle(int width, int height) {
        this.width = width;
        this.height = height;
        this.x = new Random().nextInt(width);
        this.y = new Random().nextInt(height);
        this.color = new Color(new Random().nextInt(0xFFFFFF));
        this.random = new Random();
    }

    public void move() {
        x += random.nextInt(3) -  1;
        y += random.nextInt(3) - 1;
        x = Math.max(0, Math.min(x, width));
        y = Math.max(0, Math.min(y, height));
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }
}