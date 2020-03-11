package ThreadPerConnection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(7777)){
            while (true){
                try(Socket s = serverSocket.accept()){

                    Thread thread=new Thread(new wThread(serverSocket));
                    thread.start();

                }catch (Exception e){
                    System.err.println(e);
                    System.out.println("Everyone type: Alex is a stupid cunt in chat");
                }
            }
        }catch (IOException e){
            System.out.println("Could not establish Server");
        }

    }

}


class wThread implements Runnable{
    ServerSocket serverSocket;
    wThread(ServerSocket s){
        this.serverSocket= s;
    }

    @Override
    public void run() {
        try {
            while(true) {
                Socket s = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter printWriter = new PrintWriter(s.getOutputStream());

                String msg = bufferedReader.readLine();
                if (msg != null) {
                    System.out.println("Client: " + msg);
                }
                printWriter.println("Alex");
                printWriter.flush();
            }
        }catch (IOException e){
            System.err.println(e);
        }
    }
}


class TCPAlex{

    public static void main(String[] args) {

        while (true) {
            try (Socket s = new Socket("localhost", 7777)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedReader systemInput = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter printWriter = new PrintWriter(s.getOutputStream());

                String msg = systemInput.readLine();
                printWriter.println(msg);
                printWriter.flush();

                msg = bufferedReader.readLine();
                System.out.println("Server: " + msg);


            } catch (IOException e) {
                System.out.println("Connection failed...");
            }
        }
    }

}

