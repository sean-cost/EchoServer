import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Chatterbox {

    private ServerSocket ss;
    private Socket socketConnector;


    public Chatterbox(ServerSocket ss) {
        this.ss = ss;
    }

    public Socket getSocketConnector() {
        return socketConnector;
    }

    public void start() throws IOException {
        listen();
    }

    public void listen() throws IOException {
        socketConnector = newSocketConnector();
    }

    private Socket newSocketConnector() throws IOException {
        return this.ss.accept();
    }

}
