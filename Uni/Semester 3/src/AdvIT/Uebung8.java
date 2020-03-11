package AdvIT;

public class Uebung8 {

}

class SumWorker extends Thread{

    static int MAX = 16; // Number of Worker Threads
    static int anz = 2097152;
    static int[] a= new int[anz];
    static long[] erg= new long[MAX];
    static SumWorker w[] = new SumWorker[MAX];

    int myID = -1;

    public SumWorker(int id){
        myID = id;
    }

    @Override
    public void run() {
        int start = myID*(a.length / MAX);
        int stop = (myID + 1) * (a.length / MAX);
        long sum = 0;
        for (int i = start; i < stop; i++){
            sum = sum + a[i];
//            double x = Math.log(Math.sqrt(3.1415 * (7^2)));
       }
        erg[myID]=sum;
    }

    public static void main(String args[]){
        for (int i = 0; i<a.length;i++){
            a[i]=i;
        }
        for (int i = 0; i<MAX; i++){
            w[i] = new SumWorker(i);
        }
        long date1 = System.currentTimeMillis();
        for (int i = 0; i<MAX; i++){
            w[i].start();
        }
        try {
            for (int i = 0; i < MAX; i++){
                w[i].join();
            }
        }catch (InterruptedException e){
            System.out.println(e);
        }
        long sum = 0;
        for (int i= 0; i<MAX; i++){
            sum += erg[i];
        }
        long time = System.currentTimeMillis() - date1;
        System.out.println("Used " + MAX + " Worker Threads - Result: "+ sum + " - Computing Time: " + time );
    }
}