package AdvIT;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Uebung19 {
}


class UDPServerMT{

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

            if (msg.toLowerCase().contains("connecting...")){
                msg = "Connected.";
            } else if (msg.toLowerCase().contains("read")){
                ReaderThread readerThread = new ReaderThread(in,path);
                readerThread.start();
                System.out.println(readerThread.getName() + " is running..");
                readerThread.join();
                System.out.println(readerThread.getName() + " has stopped.");
                msg = readerThread.getMsg();
            } else if (msg.toLowerCase().contains("write")){

                String fileName = splitString(msg);
                serverSend("What do you want to write, Dave?",address,port,serverSock);
                msg = serverReceive(serverSock,receive);
                WriterThread writerThread = new WriterThread(in,msg,fileName);
                writerThread.start();
                writerThread.join();
                ReaderThread readerThread = new ReaderThread(in,path);
                readerThread.start();
            }else if (msg.toLowerCase().contains("thank you")){
                msg = "You're welcome, Dave";
            } else {
                msg = "I'm sorry, Dave. I'm afraid I can't do that…";
            }

            serverSend(msg,address,port,serverSock);

        }

    }

    private static String splitString(String msg) {
        String[] msgPart = msg.split(" ");
        String[] fileDetails = msgPart[1].split(",");
        return fileDetails[0];
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

    private static void writeFile(DatagramSocket serverSock, byte[] receive, String msg, int port, InetAddress address) throws Exception {
        String fileName = splitString(msg);
        File checkFile = new File("/Users/moritzjurgens/Documents/test/" + fileName+".txt");

        if (!checkFile.exists()){
            msg = "This File does not exist, Dave \nDo you want to create a file? (Y/N)";
            serverSend(msg,address,port,serverSock);
            msg = serverReceive(serverSock,receive);
//            System.out.println(msg);
            if (msg.equalsIgnoreCase("Y")){
                File newFile = new File("/Users/moritzjurgens/Documents/test/" +msg+".txt");
            }
        }

        msg = "What do you want to write, Dave?";
        serverSend(msg, address, port, serverSock);

        msg = serverReceive(serverSock, receive);



    }

}

class ReaderThread extends Thread{

    private DatagramPacket datagramPacket;
    private String msg;
    private String path;
    private String fileName;

    public ReaderThread(DatagramPacket datagramPacket, String path){
        this.datagramPacket = datagramPacket;
        this.path = path;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        readFile();
    }

    String getMsg(){
        return msg;
    }

    private void readFile(){
        msg = new String(datagramPacket.getData(),0,datagramPacket.getLength());
        //Splitting the Message
        String[] msgPart = msg.split(" ");
        String[] fileDetails = msgPart[1].split(",");
        fileName = fileDetails[0];

        //read the file
        BufferedReader fileRead = null;
        try {
            fileRead = new BufferedReader(new FileReader(path + fileName + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String fileData = null;
        try {
            assert fileRead != null;
            fileData = fileRead.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        msg = ("Here you go, Dave: \n" + fileData);

    }



}

class WriterThread extends Thread{

    DatagramPacket datagramPacket;
    String msg;
    String fileName;

    WriterThread(DatagramPacket datagramPacket,String msg, String fileName){
        this.datagramPacket = datagramPacket;
        this.msg = msg;
        this.fileName = fileName;
    }

    @Override
    public void run() {

        try {
            FileWriter fileWrite = new FileWriter("/Users/moritzjurgens/Documents/test/" + fileName + ".txt", false);
            fileWrite.write(msg);
            fileWrite.flush();
            fileWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class UDPClient2{

    public static void main(String[] args) throws Exception {
        DatagramSocket clientSock = new DatagramSocket();
        byte[] receive = new byte[1024];
        byte[] send = new byte[1024];
        InetAddress address = InetAddress.getByName("localhost");

//      Send Greeting
        String msg = "Connecting...";
        send = msg.getBytes();
        DatagramPacket out = new DatagramPacket(send,0,send.length, address,5999);
        clientSock.send(out);
        System.out.println(msg);

//      Get Response
        DatagramPacket in = new DatagramPacket(receive,1024);

        try {
            clientSock.receive(in);
        } catch (IOException e) {
            System.out.println("Connection Failed.");
        }
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