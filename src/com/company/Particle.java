package com.company;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

public class Particle {
    private final float radius;
    private float rx, ry;
    private float vx, vy;
    private float mass;
    // count of collisions
    private int count;
    // if the particle is involved in collision
    public boolean relevant = false;
    public float time;

    public static Particle standardParticleFactory(Random r){
        return new Particle((float) 0.01, r);
    }

    public static Particle[] standardParticleArrayFactory(int num, Random r){
        Particle[] particles = new Particle[num];
        for (int i = 0; i < particles.length; i++)particles[i] = standardParticleFactory(r);
//        Arrays.fill(particles, standardParticleFactory(r));
        return particles;
    }

    public Particle(float radius, Random r)
    {
        float factor = 1000/(1-2*radius);
        this.radius = radius;
        // randomize position
        this.rx = (r.nextInt(1001)/factor)+radius;
        this.ry = (r.nextInt(1001)/factor)+radius;
        // randomize speed
        this.vx = (float)((r.nextInt(4001)/60000.0) - 2/60.0);
        this.vy = (float)((r.nextInt(4001)/60000.0) - 2/60.0);
    }
    public void draw()
    {
        // draw the balls
        StdDraw.filledCircle(rx, ry, radius);
    }
    public void move(float time) {
        // time: destination time
        if (time < this.time) return;
        float dt = time - this.time;
        this.time = time;
        // move the ball by time t
//        if ((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius)) { vx = -vx; }
//        if ((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius)) { vy = -vy; }
        this.rx += dt * this.vx;
        this.ry += dt * this.vy;
    }

    public float timeToHit(Particle that) {
        return Float.POSITIVE_INFINITY;
    }

    public float timeToHitVerticalWall() {
        return (vx > 0) ? (float) ((1.0 - radius - rx)/ vx):(rx - radius)/ -vx;
    }
    public float timeToHitHorizontalWall() {
        return (vy > 0) ? (float) ((1.0 - radius - ry)/ vy):(ry - radius)/ -vy;
    }

    public void bounceOff(Particle that) {
        // todo bounce of a particle, result in change of direction in both particles
    }

    public void bounceOffVerticalWall(){ vx = -vx; }

    public void bounceOffHorizontalWall() { vy = -vy; }

    public static void main(String[] args){
        Random r = new Random();
        System.out.println(r.nextInt(2));

    }
}
