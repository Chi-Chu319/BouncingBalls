package com.company;

import java.util.Comparator;

public class Event implements Comparable<Event> {
    // merely a class to store particles and event time.
    // time is the absolute time of the simulation
    private float time;
    public Particle a, b;

    public Event(float time, Particle a, Particle b){
        // if a is not null b is null collision on vertical wall
        // if a is null b is not null collision on horizontal wall
        this.a = a;
        this.b = b;
        this.time = time;
    }

    public float time()
    {
        return time;
    }

    public void react(){
        // react to the cases of the particles in the event
        if (a == null && b == null) return;
        else if (a != null && b == null) a.bounceOffVerticalWall();
        else if (a == null && b != null) b.bounceOffHorizontalWall();
        else if (a != null && b != null) a.bounceOff(b);
    }

    public boolean isValid(){
        // return true if the event is still valid
        // the event might be not valid because of the collision.
        return true;
    }

    @Override
    public int compareTo(Event that) {
        return (int) (this.time - that.time);
    }
}
