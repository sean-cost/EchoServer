import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {

    private ServerInterface si;
    private ServerSocket ss;

    Listener(ServerInterface si, ServerSocket ss) {
        this.si = si;
        this.ss = ss;
    }

    public StreamSocket connect() throws IOException {
        Socket socket = ss.accept();
        si.inform("Connection established with client in port " + socket.getPort());

        return new StreamSocket(socket.getInputStream(), socket.getOutputStream());
    }

}
