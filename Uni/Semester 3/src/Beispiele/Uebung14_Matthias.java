package Beispiele;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.File;
import java.io.FileReader;
import java.net.ServerSocket;

class Server {

    private static final String SAVE = "SAVE";
    private static final String KEY = "KEY";
    private static final String GET = "GET";
    private static final String OK = "OK";
    private static final String FAILED = "FAILED";

    private static final String FOLDER = "MESSAGES";

    private static int fileCounter = 0;

    public static void main(String[] args) {
        File m = new File(FOLDER);
        if (m.exists() && !m.isDirectory()) {
            m.delete();
        }
        if (m.exists() && m.isDirectory()) {
            File[] f = m.listFiles();
            int last = 0;
            for (File ff : f) {
                System.out.println(ff.getName());
                if (ff.getName().startsWith("f")) {
                    if (Integer.valueOf(ff.getName().split("f", 2)[1]) > last) {
                        last = 1 + Integer.valueOf(ff.getName().split("f", 2)[1]);
                    }
                }
            }
            fileCounter = last;
        }

        try (ServerSocket socket = new ServerSocket(7777)) {
            while (true) {
                Socket c = socket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
                PrintWriter out = new PrintWriter(c.getOutputStream());
                String[] sc = in.readLine().split(" ", 2);
                if (sc[0].equals(SAVE)) {
                    writeFile(sc[1], fileCounter);
                    out.println(KEY + " " + fileCounter);
                    fileCounter++;
                } else if (sc[0].equals(GET)) {
                    try {
                        out.println(OK + " " + readFile(sc[1]));
                    } catch (IOException e) {
                        out.println(FAILED);
                    }
                } else {
                    out.println(FAILED);
                }
                out.flush();
                c.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void writeFile(String content, int i) throws IOException {
        File dir = new File(FOLDER);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File f = new File(FOLDER + File.separator + "f" + i);
        PrintWriter fos = new PrintWriter(f);
        fos.write(content);
        fos.close();
    }

    private static String readFile(String i) throws IOException {
        File f = new File(FOLDER + File.separator + "f" + i);
        BufferedReader in = new BufferedReader(new FileReader(f));
        String content = "";
        String line = "";
        while (null != (line = in.readLine())) {
            content += line;
        }
        in.close();
        return content;
    }

}

class Client {

    public static void main(String[] args) {
        String r = testWrite();
        testReceive(r);
    }

    private static String testWrite() {
        String r = "";
        try (Socket s = new Socket("localhost", 7777)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            out.println("SAVE WOOOOP");

            out.flush();
            String rec = in.readLine();
            System.out.println(rec);
            r = rec.split(" ", 2)[1];

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return r;
    }

    private static void testReceive(String r) {
        try (Socket s = new Socket("localhost", 7777)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream());

            out.println("GET " + r);

            out.flush();
            String rec = in.readLine();
            System.out.println(rec);

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}


