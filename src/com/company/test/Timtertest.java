package com.company.test;

import java.util.Timer;
import java.util.TimerTask;

public class Timtertest extends TimerTask{

    @Override
    public void run() {
        System.out.println(this.scheduledExecutionTime());
    }



    public static void main(String[] args) {
        Timtertest task = new Timtertest();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0, 50);
        try {
            Thread.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
