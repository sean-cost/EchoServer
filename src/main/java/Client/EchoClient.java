package Client;

import StreamSocket.StreamSocket;

import java.io.IOException;
import java.net.Socket;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        //int portNumber = Integer.parseInt(args[0]);
        Socket socket = new Socket("localhost", 5000);
        StreamSocket ss = new StreamSocket(socket.getInputStream(), socket.getOutputStream());

        ClientIO ci = new ClientIO(System.in, System.out);

        Babbler client = new Babbler(ss, ci);
        client.run();
    }
}
