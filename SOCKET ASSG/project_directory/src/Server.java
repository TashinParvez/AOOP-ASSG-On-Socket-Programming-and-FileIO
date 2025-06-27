import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private static final int PORT = 5000;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            //--------------------------------  Start a thread to handle server input  --------------------------------
            new Thread(() -> handleServerInput()).start();

            //--------------------------------  Accept client connections  --------------------------------
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                //--------------------------------  Create a new client handler  --------------------------------
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //--------------------------------  Broadcast message to all clients  --------------------------------
    public static void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    //-------------------------------- Remove a client from the list --------------------------------
    public static void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
        }
    }

    //-------------------------------- Handle server console input  --------------------------------
    private static void handleServerInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            String formattedMessage = "Server: " + message;
            System.out.println(formattedMessage);
            broadcast(formattedMessage, null);
        }
    }
}