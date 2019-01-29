import java.io.IOException;
import java.net.ServerSocket;

public class EchoServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9090);
        Chatterbox server = new Chatterbox(ss);
        server.start();
    }
}
