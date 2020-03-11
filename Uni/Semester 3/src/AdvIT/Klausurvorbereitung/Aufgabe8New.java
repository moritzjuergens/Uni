package AdvIT.Klausurvorbereitung;

import java.util.concurrent.TimeUnit;

public class Aufgabe8New {

    public static void main(String[] args) throws Exception{
        int[] array = new int[2097152];
        int startID = 0;
        int stopID = 0;
        int anzThread;
        int intervall;
        anzThread = 8;

            intervall = 2097152/anzThread;
            stopID = intervall;

            long zeit = System.currentTimeMillis();
        for (int i = 1; i<=anzThread; i++){

            ArrayThread arrayThread = new ArrayThread(array,startID,stopID);
            arrayThread.start();

            startID = stopID + 1;
            stopID = startID + intervall;

            arrayThread.join();
//            System.out.println(arrayThread.getName());
        }
        long realTime = System.currentTimeMillis() - zeit;
        System.out.println(realTime);

    }

}

class ArrayThread extends Thread{
    int[] array;
    int startID;
    int stopID;
    int ergebnis;
    ArrayThread(int[] array, int startID, int stopID){
        this.array = array;
        this.startID = startID;
        this.stopID = stopID;
    }

    @Override
    public void run() {
       for(int i = startID; i <= stopID; i++){
           array[i] = 1;

//           System.out.println(i);
       }
    }

    int getErgebnis(){
        return ergebnis;
    }

}