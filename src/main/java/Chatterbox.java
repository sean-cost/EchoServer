import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Chatterbox {

    private UserInterface ui;
    private ServerSocket ss;
    private StreamSocket sc;
    private Socket socket;


    public Chatterbox(ServerSocket ss, UserInterface ui) {
        this.ss = ss;
        this.ui = ui;
    }

    public Socket getSocket() {
        return socket;
    }

    public void start() throws IOException {
        ui.inform("Starting the server");
        connect();
        sendInstructions();
        echo();
    }

    public void connect() throws IOException {
        ui.inform("Server is awaiting for connections");
        socket = newSocket();
        ui.inform("Connection established with client in port " + socket.getPort());
        setStreamSocket(socket);
    }

    public void sendInstructions() {
        String message = "Hello! Please insert a word";
        sc.printToSocket(message);
    }

    public void echo() throws IOException {
        String message = sc.readFromSocket();
        sc.printToSocket(message);
        if(sc.isMessageSent()){
            ui.inform("Message sent");
        }
    }

    private Socket newSocket() throws IOException {
        return this.ss.accept();
    }

    private void setStreamSocket(Socket sc) throws IOException {
        this.sc = new StreamSocket(sc);
    }
}
