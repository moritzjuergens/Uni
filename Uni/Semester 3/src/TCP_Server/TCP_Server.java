package TCP_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(7777)){
            while(true){

                Socket s = serverSocket.accept();
                TCP_Thread tcp_thread = new TCP_Thread(s);
                tcp_thread.start();
                tcp_thread.join();

            }
        }catch(Exception e){
            System.err.println(e);
        }
    }
}

class TCP_Thread extends Thread{

    Socket s;
    TCP_Thread(Socket s){
        this.s = s;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter printWriter = new PrintWriter(s.getOutputStream());

            String msg = bufferedReader.readLine();
            if(msg!=null) {
                System.out.println("Client: " + msg);
                printWriter.println("Hallo zurück!");
                printWriter.flush();
            }
            s.close();
        }catch (IOException e){
            System.err.println(e);
        }
    }
}

class TCP_Client{
    public static void main(String[] args) {
        while(true) {
            try (Socket s = new Socket("localhost", 7777)) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                BufferedReader SystemInput = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter printWriter = new PrintWriter(s.getOutputStream());

                String msg = SystemInput.readLine();

                printWriter.println(msg);
                printWriter.flush();

                msg= bufferedReader.readLine();
                System.out.println("Server: " + msg);
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

}