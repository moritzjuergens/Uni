package ITsecurity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.sql.Wrapper;

public class ggT {

    public static void main(String[] args) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println();
        System.out.println();
        System.out.println("Geben Sie zwei Zahlen getrennt von einem ';' ein!");
        String message = bufferedReader.readLine();
        String[] zahlen = message.split(";");

        int a = Integer.parseInt(zahlen[0]);
        int b = Integer.parseInt(zahlen[1]);
        int z = 0;
        int ggt = a;

        while (a != b) {
            if (a < b) {
                z = a;
                a = b;
                b = z;
            } else if (a % b == 0) {
                ggt = b;
                break;
            } else {
                a %= b;
            }
        }

        System.out.println("ggT: " + ggt);
    }
}
