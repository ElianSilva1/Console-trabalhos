package com.particlesimulation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleSimulation extends JPanel {
    private final List<Particle> particles;
    private final int width = 800;
    private final int height = 600;
    private final int numParticles = 1000;

    public ParticleSimulation() {
        particles = new ArrayList<>();
        for (int i = 0; i < numParticles; i++) {
            particles.add(new Particle(width, height));
        }
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Particle particle : particles) {
            g.setColor(particle.getColor());
            g.fillOval(particle.getX(), particle.getY(), 5, 5);
        }
    }

    public void updateParticlesMultiThreaded() {
        int numThreads = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[numThreads];
        int batchSize = particles.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            final int start = i * batchSize;
            final int end = (i == numThreads - 1) ? particles.size() : start + batchSize;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    particles.get(j).move();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particle Simulation");
        ParticleSimulation simulation = new ParticleSimulation();
        frame.add(simulation);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Timer(16, e -> {
            simulation.updateParticlesMultiThreaded();
            simulation.repaint();
        }).start();
    }
}