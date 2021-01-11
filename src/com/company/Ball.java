package com.company;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;

public class Ball {
    private float radius;
    private double rx, ry;
    private double vx, vy;
    private Random r = new Random();
    public Ball(float radius)
    {
        double factor = 1000/(1.0-2*radius);
        this.radius = radius;
        // randomize position
        this.rx = (this.r.nextInt(1001)/factor)+radius;
        this.ry = (this.r.nextInt(1001)/factor)+radius;
        // randomize speed
        this.vx = (r.nextInt(1001)/40000.0) + 1/40.0;
        this.vy = (r.nextInt(1001)/40000.0) + 1/40.0;
    }
    public void draw()
    {
        // draw the balls
        StdDraw.filledCircle(rx, ry, radius);
    }
    public void move(double dt)
    {
        // todo move the ball by time t
        // todo hit the wall
        if ((rx + vx*dt < radius) || (rx + vx*dt > 1.0 - radius)) { vx = -vx; }
        if ((ry + vy*dt < radius) || (ry + vy*dt > 1.0 - radius)) { vy = -vy; }
        this.rx += dt * this.vx;
        this.ry += dt * this.vy;
    }
    public static void main(String[] args){
        Random r = new Random();
        System.out.println(r.nextInt(2));

    }
}
