package AdvIT;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Uebung14 {
    void run() {
    }
}

class Server {

    public static void main(String args[]) throws Exception {

        ServerSocket s = new ServerSocket(7777);
        Socket c = s.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
        String msg = in.readLine();
        System.out.println("Client: " + msg);
        s.close();

        while (true){

//            s = new ServerSocket(7777);
            c = s.accept();
            PrintWriter out = new PrintWriter(c.getOutputStream());
            out.println("Client Connected");
            out.flush();
            s.close();


//            s = new ServerSocket(7777);
            c = s.accept();
            in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            msg = in.readLine();
            System.out.println("Client: " + msg);
            s.close();

        }
    }
}

class Client{


    public static void main(String args[]) throws Exception {

        Socket c = new Socket("localhost", 7777);
        PrintWriter out = new PrintWriter(c.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String rec = "Connecing...";
        System.out.println(rec);

        out.print(rec);
        out.flush();

        c = new Socket("localhost", 7777);
        in = new BufferedReader(new InputStreamReader(c.getInputStream()));
        String msg = in.readLine();
        System.out.println("Server: " + msg);

        while (true){
            c = new Socket("localhost", 7777);
            out = new PrintWriter(c.getOutputStream());
            in = new BufferedReader(new InputStreamReader(System.in));

            rec = in.readLine();
            System.out.println(rec);

            out.print(rec);
            out.flush();

            c = new Socket("localhost", 7777);
            in = new BufferedReader(new InputStreamReader(c.getInputStream()));
            msg = in.readLine();
            System.out.println("Server: " + msg);
        }
    }

}