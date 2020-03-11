package Monitor;

import java.io.*;
import java.sql.Time;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Monitor {

    int anzWriter = 0;
    int anzReader = 0;
    boolean activeWriter = false;

    public void startRead() throws Exception{

        while (anzWriter>0){
            try {
                this.wait();
            }catch (Exception e){
                System.err.println(e);
            }
            anzReader++;
        }
    }

    public void stopRead(){
        anzReader--;
        if(anzReader==0){
            this.notifyAll();
        }
    }

    public synchronized void startWrite() throws Exception{
        anzWriter++;
        while (anzReader>0 && activeWriter==true){
            this.wait();
        }
        activeWriter=true;
    }

    public synchronized void stopWrite(){
        anzWriter--;
        activeWriter= false;
        this.notifyAll();
    }

}

class Wthread extends Thread{
    Monitor monitor;

    Wthread(Monitor monitor){
        this.monitor = monitor;
    }

    void read() throws Exception{
        final String path = "/users/moritzjurgens/documents/test/test.txt";
        BufferedReader reader = new BufferedReader(new FileReader(path));
        System.out.println(reader.readLine());
    }
    void write()throws Exception{
        final String path = "/users/moritzjurgens/documents/test/test.txt";
        FileWriter fileWriter = new FileWriter(path);
        String msg = String.valueOf(new Random().nextInt());
        System.out.println(msg);
        fileWriter.write(msg);
        fileWriter.flush();
    }

    @Override
    public void run() {
        try{

            monitor.startRead();
            this.read();
            monitor.stopRead();
            monitor.startWrite();
            this.write();
            monitor.stopWrite();

        }catch(Exception e){
            System.err.println(e);
        }
    }

    public static void main(String[] args) throws Exception{

        Monitor m = new Monitor();

        for (int i= 0; i<6; i++){
            Wthread c = new Wthread(m);
            c.start();
            System.out.println("Thread " + i + " is running..");
            TimeUnit.SECONDS.sleep(2);
        }
        TimeUnit.SECONDS.sleep(2);


    }

}



// K.A. = Lesen & Schreiben