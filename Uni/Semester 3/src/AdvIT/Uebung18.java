package AdvIT;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;


public class Uebung18 {

    public static void main(String[] args) throws Exception{

        UDPServer udpServer = new UDPServer();
        UDPClient udpClient = new UDPClient();

       /* udpServer.run();
        TimeUnit.SECONDS.sleep(5);
        udpClient.run();*/

    }

}

class UDPServer{

    public static void main(String[] args) throws Exception {
        DatagramSocket serverSock = new DatagramSocket(5999);
        byte[] receive = new byte[1024];
        byte[] send = new byte[1024];
        final String path = "/Users/moritzjurgens/Documents/test/";


        while (true) {
//      Receive
            DatagramPacket in = new DatagramPacket(receive, 1024);
            serverSock.receive(in);
            String msg = new String(in.getData(), 0, in.getLength());
            System.out.println("Dave: " + msg);


//      Send Response
            int port = in.getPort();
            InetAddress address = in.getAddress();

            if (msg.toLowerCase().contains("connected")){
                msg = "Affirmative";
            } else if (msg.toLowerCase().contains("read")){
                String fileName = splitString(msg);
                msg = readFile(path, msg,fileName);
            } else if (msg.toLowerCase().contains("write")){
                String fileName = splitString(msg);
                msg = writeFile(serverSock, receive, path, msg, port, address);
            }else if (msg.toLowerCase().contains("thank you")){
                msg = "You're welcome, Dave";
            }else {
                msg = "I'm sorry, Dave. I'm afraid I can't do that…";
            }

            serverSend(msg,address,port,serverSock);

        }

    }

    private static String readFile(String path, String msg,String fileName) throws IOException {
//        String fileName = splitString(msg);
//                msg = "You want me to open " + fileName + ", Dave?";
        BufferedReader fileRead = new BufferedReader(new FileReader(path + fileName + ".txt"));
        String fileData = fileRead.readLine();
        msg = ("Here you go, Dave: \n" + fileData);
        return msg;
    }

    private static String splitString(String msg) {
        String[] msgPart = msg.split(" ");
        String[] fileDetails = msgPart[1].split(",");
        return fileDetails[0];
    }

    private static String writeFile(DatagramSocket serverSock, byte[] receive, String path, String msg, int port, InetAddress address) throws Exception {
        String fileName = splitString(msg);
        File checkFile = new File(path + fileName+".txt");

        if (!checkFile.exists()){
            msg = "This File does not exist, Dave \nDo you want to create a file? (Y/N)";
            serverSend(msg,address,port,serverSock);
            msg = serverReceive(serverSock,receive);
//            System.out.println(msg);
            if (msg.equalsIgnoreCase("Y")){
                File newFile = new File(path+msg+".txt");
            }else{
                return "Action aborted…";
            }
        }

            msg = "What do you want to write, Dave?";
            serverSend(msg, address, port, serverSock);

            msg = serverReceive(serverSock, receive);

            FileWriter fileWrite = new FileWriter(path + fileName + ".txt", false);
            fileWrite.write(msg);
            fileWrite.flush();
            fileWrite.close();

            msg = readFile(path,msg,fileName);


        return msg;
    }

    private static void serverSend(String msg, InetAddress address, int port, DatagramSocket serverSock) throws Exception{
        byte[] send = msg.getBytes();
        DatagramPacket out = new DatagramPacket(send, send.length, address, port);
        serverSock.send(out);
        System.out.println(msg);
    }

    private static String serverReceive(DatagramSocket serverSock, byte[] receive) throws Exception{
        DatagramPacket in = new DatagramPacket(receive, 1024);
        serverSock.receive(in);
        String msg = new String(in.getData(), 0, in.getLength());
        System.out.println("Dave: " + msg);
        return msg;
    }


}

class UDPClient{

    public static void main(String[] args) throws Exception {
        DatagramSocket clientSock = new DatagramSocket();
        byte[] receive = new byte[1024];
        byte[] send = new byte[1024];
        InetAddress address = InetAddress.getByName("localhost");

//      Send Greeting
        String msg = "Client Connected";
        send = msg.getBytes();
        DatagramPacket out = new DatagramPacket(send,0,send.length, address,5999);
        clientSock.send(out);
        System.out.println(msg);

//      Get Response
        DatagramPacket in = new DatagramPacket(receive,1024);
        clientSock.receive(in);
        msg = new String(in.getData(),0 ,in.getLength());
        System.out.println("HAL 9000: " + msg);



        while (true){
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            msg = bufferedReader.readLine();
            if (msg.equalsIgnoreCase(".")){
                break;
            }
            send = msg.getBytes();
            out = new DatagramPacket(send,0,send.length, address,5999);
            clientSock.send(out);

//          Receive Answer
            in = new DatagramPacket(receive,1024);
            clientSock.receive(in);
            msg = new String(in.getData(),0 ,in.getLength());
            System.out.println("HAL 9000: " + msg);
        }
        clientSock.close();

    }
}