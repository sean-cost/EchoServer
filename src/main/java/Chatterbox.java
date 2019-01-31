import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Chatterbox {

    private ServerSocket ss;
    private StreamSocket sc;
    private Socket socket;


    public Chatterbox(ServerSocket ss) {
        this.ss = ss;
    }

    public Socket getSocket() {
        return socket;
    }

    public void start() throws IOException {
        connect();
        echo();
    }

    public void connect() throws IOException {
        socket = newSocket();
        setStreamSocket(socket);
    }

    public void echo() throws IOException {
        String message = sc.readFromSocket();
        sc.printToSocket(message);
    }

    private Socket newSocket() throws IOException {
        return this.ss.accept();
    }

    private void setStreamSocket(Socket sc) throws IOException {
        this.sc = new StreamSocket(sc);
    }
}
