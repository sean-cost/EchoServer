import java.io.IOException;
import java.net.ServerSocket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(portNumber);

        Chatterbox server = new Chatterbox(ss, new UserInterface(System.out));
        server.start();
    }
}
