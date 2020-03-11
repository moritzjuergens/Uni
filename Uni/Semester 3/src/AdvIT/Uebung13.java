package AdvIT;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Uebung13 {

    private Fork fork1 = new Fork("Fork1");
    private Fork fork2 = new Fork("Fork2");
    private Fork fork3 = new Fork("Fork3");
    private Fork fork4 = new Fork("Fork4");
    private Fork fork5 = new Fork("Fork5");
    private Philosopher p1 = new Philosopher("p1",fork1,fork2);
    private Philosopher p2 = new Philosopher("p2",fork2,fork3);
    private Philosopher p3 = new Philosopher("p3",fork3, fork4);
    private Philosopher p4 = new Philosopher("p4",fork4, fork5);
    private Philosopher p5 = new Philosopher("p5",fork5, fork1);


    public void run(){

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

    }

}

class Philosopher extends Thread{

    private Fork left;
    private Fork right;
    private String name;

    public Philosopher(String name, Fork left, Fork right){
        this.name = name;
        this.left = left;
        this.right = right;
        //eat();
    }

    @Override
    public void run() {
        eat();
    }

    public void eat(){

        if(!left.isOccupied() && !right.isOccupied()){
            try {
                left.take();
                System.out.println(name + " has taken " + left.name);
                right.take();
                System.out.println(name + " has taken " + right.name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                System.out.println(name + " is eating");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(name + " has stopped eating");
            left.stopUsing();
            right.stopUsing();
            think();
        }else{think();}

    }

    public void think(){
        try {
            System.out.println(name + " is thinking");
            TimeUnit.SECONDS.sleep(5);
            eat();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

}

class Fork{

    boolean used;
    String name;

    public Fork(String name){
        used = false;
        this.name= name;
    }

    public boolean isOccupied(){
        return used;
    }

    public void take() throws InterruptedException {
        if(!used){
            wait();
        }

    }
    public void stopUsing(){
        used = false;
        notifyAll();
    }

}