import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //------------------------------ Read client name  --------------------------------
            clientName = in.readLine();
            if (clientName == null) {
                throw new IOException("Client disconnected before sending name");
            }
            System.out.println(clientName + " joined the network");

            //-------------------------------- Broadcast join message  --------------------------------
            String joinMessage = clientName + " has joined the network";
            Server.broadcast(joinMessage, this);

            //-------------------------------- for Read messages from client  --------------------------------
            String message;
            while ((message = in.readLine()) != null) {
                String formattedMessage = clientName + ": " + message;
                System.out.println(formattedMessage);
                Server.broadcast(formattedMessage, this);
            }
        } catch (IOException e) {
            System.out.println((clientName != null ? clientName : "Unknown client") + " disconnected: " + e.getMessage());
        } finally {
            //-------------------------------- when client disconnect --------------------------------
            Server.removeClient(this);
            String leaveMessage = (clientName != null ? clientName : "Unknown client") + " has left the network";
            Server.broadcast(leaveMessage, this);
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------------------------- Send message to this client --------------------------------
    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        } else {
            System.out.println("Cannot send message to " + (clientName != null ? clientName : "unknown client") + ": PrintWriter is null");
        }
    }
}