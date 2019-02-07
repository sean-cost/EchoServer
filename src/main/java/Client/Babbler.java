package Client;

import StreamSocket.StreamSocket;

import java.io.IOException;

public class Babbler implements Runnable {
    private ClientIO ci;
    private StreamSocket ss;

    public Babbler(StreamSocket ss, ClientIO ci) {
        this.ss = ss;
        this.ci = ci;
    }

    public void run() {
        while (true) {
            try {
                receive();
                String message = ci.getInput();
                if (!wantsToStop(message)) {
                    send(message);
                } else {
                    ss.closeConnection();
                    return;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) {
        ss.printToSocket(message);
    }

    public void receive() throws IOException {
        ci.inform(ss.readFromSocket());
    }

    private boolean wantsToStop(String message) {
        return ((message == null) || message.equalsIgnoreCase("QUIT"));
    }

}
