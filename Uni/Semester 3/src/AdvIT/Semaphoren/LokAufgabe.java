package AdvIT.Semaphoren;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class LokAufgabe {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1,true);
        Semaphore semaphoreLok1 = new Semaphore(1,true);
        Semaphore semaphoreLok2 = new Semaphore(0,true);
        lok1 lok1 = new lok1(semaphore,10,semaphoreLok1,semaphoreLok2);
        lok2 lok2 = new lok2(semaphore,5,semaphoreLok1,semaphoreLok2);

        lok1.start();
        lok2.start();
    }

}

class lok1 extends Thread{

    Semaphore semaphore;
    Semaphore semaphoreLok1;
    Semaphore semaphoreLok2;
    int velocity;
    lok1(Semaphore semaphore, int velocity, Semaphore semaphoreLok1, Semaphore semaphoreLok2){
        this.semaphore = semaphore;
        this.velocity = velocity;
        this.semaphoreLok1 = semaphoreLok1;
        this.semaphoreLok2 = semaphoreLok2;
    }

    @Override
    public void run() {
        try{
           while(true) {
            semaphoreLok1.acquire();
            semaphore.acquire();
            System.out.println("Lok 1 befindet sich im Mittelabschnitt");
            TimeUnit.SECONDS.sleep(20/velocity);
            semaphore.release();
            semaphoreLok2.release();
            System.out.println("Lok 1 fährt eine Runde");
            TimeUnit.SECONDS.sleep(60/velocity);}
        }catch (Exception e){

        }
    }
}

class lok2 extends Thread{

    Semaphore semaphore;
    Semaphore semaphoreLok1;
    Semaphore semaphoreLok2;
    int velocity;
    lok2(Semaphore semaphore, int velocity, Semaphore semaphoreLok1, Semaphore semaphoreLok2){
        this.semaphore = semaphore;
        this.velocity = velocity;
        this.semaphoreLok1 = semaphoreLok1;
        this.semaphoreLok2 = semaphoreLok2;
    }

    @Override
    public void run() {
        try{
            while(true) {
                semaphoreLok2.acquire();
                semaphore.acquire();
                System.out.println("Lok 2 ist im Mittelabschnitt");
                TimeUnit.SECONDS.sleep(20/velocity);
                semaphore.release();
                semaphoreLok1.release();
                System.out.println("Lok 2 fährt eine Runde");
                TimeUnit.SECONDS.sleep(60/velocity);}
        }catch (Exception e){

        }
    }
}