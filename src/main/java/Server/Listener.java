package Server;

import StreamSocket.StreamSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener {

    private ServerIO si;
    private ServerSocket ss;

    public Listener(ServerIO si, ServerSocket ss) {
        this.si = si;
        this.ss = ss;
    }

    public StreamSocket connect() throws IOException {
        Socket socket = ss.accept();
        si.inform("Connection established with client in port " + socket.getPort());

        return new StreamSocket(socket.getInputStream(), socket.getOutputStream());
    }

}
