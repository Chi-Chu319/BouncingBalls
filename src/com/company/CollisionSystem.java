package com.company;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

public class CollisionSystem {
    private MinPQ<Event> pq;
    private float t = 0;
    private final Particle[] particles;
    private final int interval;

    private class relevantParticles implements Iterable<Particle>{
        HashSet<Particle> particlesSet;
        ArrayList<Particle> particleArray;
        public relevantParticles(){
            particlesSet = new HashSet<>();
            particleArray = new ArrayList<>();
        }

        public void add(Particle particle){
            // hashset does not allow duplicate items in the set.
            if (particle != null && !particlesSet.contains(particle)) {particlesSet.add(particle); particleArray.add(particle);}
        }

        @Override
        public Iterator<Particle> iterator() {
            return particlesSet.iterator();
        }
        public Particle[] toArray(){
            return particleArray.toArray(new Particle[0]);
        }

    }

    public CollisionSystem(Particle[] particles, int refreshInterval){
        this.particles = particles;
        this.interval = refreshInterval;
        // initialize all fields
        pq = new MinPQ<>();

    }

    private void predict(Particle a){
        // predict all the event of the
        if (a == null) return;
        for (Particle b : particles) {
            float dt = a.timeToHit(b);
            pq.insert(new Event(dt + t, a, b));
        }
        pq.insert(new Event(t + a.timeToHitVerticalWall(), a, null));
        pq.insert(new Event(t + a.timeToHitHorizontalWall(), null, a));
    }

    private void predictWithoutBallCollisions(Particle a){
        if (a == null)return;
        Event e_vertical = new Event(t + a.timeToHitVerticalWall(), a, null);
        Event e_horizontal = new Event(t + a.timeToHitHorizontalWall(), null, a);
        pq.insert((e_vertical.time() > e_horizontal.time()) ? e_horizontal:e_vertical);
    }
    
    private void redraw(){
        // redraw all the particles
        StdDraw.clear();
        Stream.of(particles).parallel().forEach(Particle::draw);
    }

    private void move(Particle[] ps, float time){
        // move the relevant particles
        Stream.of(ps).parallel().forEach(p->p.move(time));
    }

    private void move(float time){
        // move the irrelevant particles
        Stream.of(particles).parallel().filter(p->!p.relevant).forEach(p->p.move(time));
    }

    private void resetParticles(){
        Stream.of(particles).parallel().filter(particle -> particle.relevant).forEach(particle -> particle.relevant = false);
    }


//    public void simulate(){
//        // initialize the priority queue
//        for (Particle particle:particles) predict(particle);
//        while (!pq.isEmpty()){
//            Event e = pq.delMin();
//            if (!e.isValid()) continue;
//            // draw all the particle by the time of the event
//            move(e.time() - t);
//            redraw();
//
//            // resolve collision
//            e.react();
//
//            // reset time to the time of event
//            t = e.time();
//            // todo redraw periodically
//            predict(e.a);
//            predict(e.b);
//        }
//
//    }

    public void simulateWithoutBallCollisions(){
        // initializing the pq
        for (Particle particle:particles) predictWithoutBallCollisions(particle);

        // create timer and timerTask
        particleTimer timerTask = new particleTimer();
        Timer timer = new Timer(true);
        // repeat ever 50 milli-seconds
        timer.scheduleAtFixedRate(timerTask, 0, interval);
        try {
            Thread.sleep(1200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
        System.out.println("TimerTask finished");
    }

    private class particleTimer extends TimerTask{
        @Override
        public void run() {
            // redraw all the balls
            redraw();
            relevantParticles relevant = new relevantParticles();
            // time for particles relevant in the collision
            float finishtime = (float) (t + interval * 0.001);
            while (true){
                // peek into the min entry
                Event peek = pq.min();
                if (peek.time() >= finishtime) break;
                // resolve the event
                Event e = pq.delMin();
                Particle p = (e.a != null) ? e.a:e.b;
                // mark all relevant particles
                p.relevant = true;
                // hashset will prevent overlapping items in list
                relevant.add(p);
                move(relevant.toArray(), e.time());
                e.react();
                predictWithoutBallCollisions(p);

            }
            move(finishtime);
            resetParticles();
            t = finishtime;
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Random r = new Random();
        Particle[] particles = Particle.standardParticleArrayFactory(N, r);
        CollisionSystem c = new CollisionSystem(particles, 50);
        c.simulateWithoutBallCollisions();
    }
}
