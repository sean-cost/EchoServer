import java.io.IOException;
import java.net.ServerSocket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(portNumber);
        UserInterface ui = new UserInterface(System.out);

        Chatterbox server = new Chatterbox(ss, ui);
        server.start();
    }
}
