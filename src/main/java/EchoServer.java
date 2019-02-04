import java.io.IOException;
import java.net.ServerSocket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);

        ServerSocket ss = new ServerSocket(portNumber);
        ServerInterface si = new ServerInterface(System.out);

        Listener listener = new Listener(si, ss);
        Chatterbox server = new Chatterbox(listener, si);

        server.start();
    }
}
