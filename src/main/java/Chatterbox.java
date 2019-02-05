import java.io.*;

public class Chatterbox {

    private final Listener listener;
    private ServerInterface si;


    public Chatterbox(Listener listener, ServerInterface si){
        this.listener = listener;
        this.si = si;
    }

    public void start() throws IOException {
        si.inform("Starting the server");

        while(true){
            si.inform("Server is awaiting for connections");
            StreamSocket sc = listener.connect();
            new EchoThread(sc, si).start();
        }
    }

}

