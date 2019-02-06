package Server;

import StreamSocket.StreamSocket;

import java.io.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Chatterbox {

    private final Listener listener;
    private ServerIO si;
    private Executor executor;


    public Chatterbox(Listener listener, ServerIO si) {
        this.listener = listener;
        this.si = si;
        this.executor = Executors.newFixedThreadPool(10);
    }

    public void start() throws IOException {
        si.inform("Starting the server");

        while (true) {
            si.inform("Server is awaiting for connections");
            StreamSocket sc = listener.connect();
            executor.execute(new EchoRunnable(sc, si));
        }
    }

}

