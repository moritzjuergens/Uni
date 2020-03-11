import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
//    Cunt
}
class Server{

    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(7777);

        while (true){
            Socket s = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter writer = new PrintWriter(s.getOutputStream());

            String msg = reader.readLine();
            System.out.println(msg);

            if (msg.toLowerCase().contains("alex")){
                writer.println("He is a stupid n#####");
                writer.flush();
            } else{
                writer.println("Cunt");
                writer.flush();
            }

        }

    }

}
class Client{
    public static void main(String[] args) throws Exception {


        while(true){

        Socket s = new Socket("localhost",7777);
        BufferedReader sysReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer = new PrintWriter(s.getOutputStream());

            String msg = sysReader.readLine();
            writer.println(msg);
            writer.flush();

            msg = reader.readLine();
            System.out.println(msg);

        }
    }
}