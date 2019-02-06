package Client;

import StreamSocket.StreamSocket;

import java.io.IOException;

public class Babbler implements Runnable{
    private ClientIO ci;
    private StreamSocket ss;

    public Babbler(StreamSocket ss, ClientIO ci) {
        this.ss = ss;
        this.ci = ci;
    }

    public void run() {
        try {
            listen(ci.getInput());
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(String message) {
        ss.printToSocket(message);
    }

    public void receive() throws IOException {
        ci.inform(ss.readFromSocket());
    }

}
