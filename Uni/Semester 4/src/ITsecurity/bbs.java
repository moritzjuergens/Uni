package ITsecurity;


public class bbs {

    public static void main(String[] args) {

        int p = 191;
        int q = 167;
        int s = 599;
        int n = p * q;
        int[] x = new int[101];
        int[] z = new int[101];
        x[0] = (int) Math.pow(s, 2) % n;

        for (int i = 1; i <= 100; i++) {
            x[i] = (int) Math.pow(x[i - 1], 2) % n;
            z[i] = x[i] % 2;
        }

        for (int number : z) {
            System.out.print(number);
        }


    }
}