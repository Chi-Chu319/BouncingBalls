
package com.company;


import edu.princeton.cs.algs4.StdDraw;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BouncingBalls_Timer extends TimerTask {

    private static Ball[] balls;
    @Override
    public void run() {
        StdDraw.clear();
        for (Ball ball:balls){
            ball.move((float) 0.5);
            ball.draw();
        }
        StdDraw.show();
        completeTask();
    }

    private void completeTask() {
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        balls = new Ball[N];
        Random r = new Random();
        for(int i = 0; i < N; i++){
            balls[i] = new Ball((float) 0.005, r);
        }
        BouncingBalls_Timer timerTask = new BouncingBalls_Timer();
        // running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 50);
        System.out.println("TimerTask started");
        //cancel after sometime
        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask finished");
    }

}
