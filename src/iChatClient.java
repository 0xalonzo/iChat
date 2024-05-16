import java.io.*;
import java.net.*;

public class iChatClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ChatClient <server_ip> <port_number>");
            return;
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to server at " + serverAddress + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            Thread receiveThread = new Thread(() -> {
                String inputLine;
                try {
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Server: " + inputLine);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading from server: " + e.getMessage());
                }
            });

            receiveThread.start();

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println("Client: " + userInput);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to connect to " + serverAddress + " on port " + port);
            System.out.println(e.getMessage());
        }
    }
}
