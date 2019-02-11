package Server;

import StreamSocket.StreamSocket;

import java.io.IOException;

public class EchoRunnable implements Runnable {

    private StreamSocket sc;
    private ServerIO si;
    private boolean isStopRequested;

    public EchoRunnable(StreamSocket sc, ServerIO si) {
        this.sc = sc;
        this.si = si;
    }

    public void run() {
        String message;

        while (!isStopRequested()) {
            try {
                message = receive();
                if (wantsToStop(message)) {
                    stop();
                } else {
                    echo(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void requestStop() {
        isStopRequested = true;
    }

    public synchronized boolean isStopRequested() {
        return isStopRequested;
    }

    private void echo(String message) {
        sc.printToSocket(message);
        if (sc.isMessageSent()) {
            si.inform("Message sent");
        }
    }

    private String receive() throws IOException {
        return sc.readFromSocket();
    }

    private void stop() throws IOException {
        sc.closeConnection();
        requestStop();
    }

    private boolean wantsToStop(String message) {
        return ((message == null) || message.equalsIgnoreCase("QUIT"));
    }

}
