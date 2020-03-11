package AdvIT;
import  java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Uebung11  {

    private Semaphore semaphore = new Semaphore(1, true);
    private Semaphore nextLok0 = new Semaphore(1,true);
    private Semaphore nextLok1 = new Semaphore(0,true);
    private int lokNext = 0;
    private int middleDistance = 20;
    private int remainingDistance = 60;

    public void run(){
        lok0 lok0 = new lok0(semaphore,5, nextLok0, nextLok1 );
        lok1 lok1 = new lok1(semaphore,10, nextLok0, nextLok1);

        lok0.start();
        lok1.start();
    }

}

class lok0 extends Thread{

    private Semaphore semaphore;
    private int velocity;
    private Semaphore nextLok1;
    private Semaphore nextLok0;

    lok0(Semaphore semaphore, int velocity, Semaphore nextLok0, Semaphore nextLok1){

        this.velocity = velocity;
        this.semaphore = semaphore;
        this.nextLok0 = nextLok0;
        this.nextLok1 = nextLok1;

    }

    @Override
    public void run(){

        while(true){
            try{
                // enter the middle part
                this.nextLok0.acquire();
                this.semaphore.acquire();
                System.out.println("Lok 0 entered the middle piece");
                // drive through the middle part
                TimeUnit.SECONDS.sleep(20/this.velocity);

                this.semaphore.release();
                this.nextLok1.release();
                System.out.println("Lok 0 left the middle piece");
                TimeUnit.SECONDS.sleep(60/this.velocity);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }

    }

}

class lok1 extends Thread{

    private Semaphore semaphore;
    private int velocity;
    private Semaphore nextLok1;
    private Semaphore nextLok0;

    lok1(Semaphore semaphore, int velocity, Semaphore nextLok0, Semaphore nextLok1){

        this.velocity = velocity;
        this.semaphore = semaphore;
        this.nextLok0 = nextLok0;
        this.nextLok1 = nextLok1;
    }

    @Override
    public void run(){
        try {
            TimeUnit.SECONDS.sleep(60/this.velocity);
        } catch (InterruptedException e){
            System.out.println(e);
        }
        while(true){
            try{
                // enter the middle part
                this.nextLok1.acquire();
                this.semaphore.acquire();
                System.out.println("Lok 1 entered the middle piece");
                // drive through the middle part
                TimeUnit.SECONDS.sleep(20/this.velocity);

                this.semaphore.release();
                this.nextLok0.release();
                System.out.println("Lok 1 left the middle piece");
                TimeUnit.SECONDS.sleep(60/this.velocity);
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }

    }

}