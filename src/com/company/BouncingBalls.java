package com.company;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BouncingBalls {

    public static void main(String[] args) throws InterruptedException {
	// write your code here
        int N = Integer.parseInt(args[0]);
        Ball[] balls = new Ball[N];
        Random r = new Random();
        for(int i = 0; i < N; i++){
            balls[i] = new Ball( (float) 0.005, r);
        }
        while (true){
            StdDraw.clear();
            for (Ball ball:balls){
                ball.move((float) 0.5);
                ball.draw();
            }
            StdDraw.show();
            TimeUnit.MILLISECONDS.sleep(50);

        }

    }
    public static void DrawWalls(){

    }
}
