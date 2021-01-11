package com.company;

import edu.princeton.cs.algs4.StdDraw;

import javax.swing.*;
import java.util.Random;

public class Ball {
    private final float radius;
    private float rx, ry;
    private float vx, vy;
    public Ball(float radius, Random r)
    {
        double factor = 1000/(1.0-2*radius);
        this.radius = radius;
        // randomize position
        this.rx = (float)(r.nextInt(1001)/factor)+radius;
        this.ry = (float)(r.nextInt(1001)/factor)+radius;
        // randomize speed
        this.vx = (float)((r.nextInt(1001)/60000.0) + 1/60.0);
        this.vy = (float)((r.nextInt(1001)/60000.0) + 1/60.0);
    }
    public void draw()
    {
        // draw the balls
        StdDraw.filledCircle(rx, ry, radius);
    }
    public void move(float dt)
    {
        // move the ball by time t
        // hit the wall
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
