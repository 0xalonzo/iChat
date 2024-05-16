import java.io.*;
import java.net.*;

public class App {
    private static final int PORT = 6000;

    public static void main(String[] args) {
        System.out.println("Server started, listening on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection established with " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            Thread receiveThread = new Thread(() -> {
                String inputLine;
                try {
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Client: " + inputLine);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from client: " + e.getMessage());
                }
            });

            receiveThread.start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println("Server: " + userInput);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + PORT + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
