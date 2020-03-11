package AdvIT.Klausurvorbereitung;


import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Aufgabe11 {

    public static void main(String[] args) throws Exception {

        Semaphore semLok1 = new Semaphore(1,true);
        Semaphore semLok2 = new Semaphore(0,true);
        Semaphore semaphore0 = new Semaphore(1,true);

        Lok1 lok1 = new Lok1(15,semLok1,semLok2,semaphore0);
        Lok2 lok2 = new Lok2(10,semLok1,semLok2,semaphore0);
        lok1.start();
        lok2.start();
    }


}

class Lok1 extends Thread{

    private int geschwindigkeit;
    private Semaphore semLok1;
    private Semaphore semLok2;
    private Semaphore semaphore;

    Lok1(int geschwindigkeit, Semaphore semLok1,Semaphore semLok2,Semaphore semaphore){
        this.geschwindigkeit = geschwindigkeit;
        this.semLok1 = semLok1;
        this.semLok2 = semLok2;
        this.semaphore= semaphore;
    }

    @Override
    public void run() {
        while(true){
            try {
                semLok1.acquire();
                this.semaphore.acquire();
                System.out.println("Günna" + " is innä Midde");

                TimeUnit.SECONDS.sleep(20/this.geschwindigkeit);

                this.semaphore.release();
                semLok2.release();
                System.out.println("Günna" + " is raus");

                TimeUnit.SECONDS.sleep(60/this.geschwindigkeit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Lok2 extends Thread{

   private int geschwindigkeit;
   private Semaphore semLok1;
   private Semaphore semLok2;
   private Semaphore semaphore;

    Lok2(int geschwindigkeit, Semaphore semLok1,Semaphore semLok2,Semaphore semaphore){

        this.geschwindigkeit = geschwindigkeit;
        this.semLok1 = semLok1;
        this.semLok2 = semLok2;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        try {
            TimeUnit.SECONDS.sleep(60/this.geschwindigkeit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true){
            try {
                semLok2.acquire();
                this.semaphore.acquire();
                System.out.println("Hannes" + " is innä Midde");

                TimeUnit.SECONDS.sleep(20/this.geschwindigkeit);

                this.semaphore.release();
                semLok1.release();
                System.out.println("Hannes" + " is raus");

                TimeUnit.SECONDS.sleep(60/this.geschwindigkeit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}