import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class HTTPClient {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("www.vstup.de", 80);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        BufferedReader systemReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //String string = systemReader.readLine();
        printWriter.println("GET /faq");
        printWriter.println("Host: www.vstup.de");
        printWriter.println("Accept: text/html");
        printWriter.println("");
        printWriter.flush();

        String message = bufferedReader.readLine();
        System.out.println("Server:" + message);

        var line = "";
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(bufferedReader.readLine());
        }
//              "GET /faq.html HTTP/1.1"
        if (message.contains("HTTP/1.1 400")) {
            socket.close();
        }

    }

}

class HTTPClient2 {

    public static void main(String[] args) {

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://vstup.de/faq"))
                .build();

        httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();
    }

}