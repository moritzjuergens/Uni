package AdvIT.Klausurvorbereitung;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Aufgabe14 {
}

class Client{
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost", 7777);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        PrintWriter pr = new PrintWriter(s.getOutputStream());
    while (true) {

//      Get an Input and check it
        String msg = input.readLine();
        if (msg.equalsIgnoreCase(".")){
            break;
        }

//      Take the Input and filter it, then flush it out
        pr.println(msg);
        pr.flush();

//      Get an Answer
        String str = bf.readLine();
        System.out.println("Server: " + str);
    }
        s.close();
    }
}

class Server{
    public static void main(String[] args) throws Exception {
//          Set the Server on a fixed Port
            ServerSocket serverSocket = new ServerSocket(7777);
        while(true) {
//          Accept the clients connection
            Socket s = serverSocket.accept();
            System.out.println("Client connected");

//          Get a message from client
            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);

            String str = bf.readLine();
            System.out.println("Client: " + str);


//          Answer accordingly
            if (str.toLowerCase().contains("hello")){
                PrintWriter pr = new PrintWriter(s.getOutputStream());
                pr.println("Oi, Cunt!");
                pr.flush();
            }else if(str.toLowerCase().contains("Whats up")){
                PrintWriter pr = new PrintWriter(s.getOutputStream());
                pr.println("The Sky, Cunt. What about you?");
                pr.flush();
            }

        }
    }
}