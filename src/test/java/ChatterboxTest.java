import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatterboxTest {

    @Test
    public void theServerEchoesAMessage() throws IOException, InterruptedException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ServerSocket ss = new ServerSocket(5000);

        new Thread(() -> {
            try {
                ServerInterface si = new ServerInterface(new PrintStream(out));
                Chatterbox server = new Chatterbox(new Listener(si, ss), si);
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Socket socket = new Socket("localhost", 5000);
        socket.getOutputStream().write("Hello\n".getBytes());

        Thread.sleep(50);

        assertThat(out.toString().contains("Message sent"), is(true));
        ss.close();
    }

    @Test
    public void theServerAllowsMultipleClients() throws IOException, InterruptedException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        ServerSocket ss = new ServerSocket(5000);

        new Thread(() -> {
            try {
                ServerInterface si = new ServerInterface(new PrintStream(out));
                Chatterbox server = new Chatterbox(new Listener(si, ss), si);
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Socket oneSocket = new Socket("localhost", 5000);
        oneSocket.getOutputStream().write("Hello world\n".getBytes());

        Socket twoSocket = new Socket("localhost", 5000);
        twoSocket.getOutputStream().write("Hello again\n".getBytes());

        Thread.sleep(50);

        assertThat(out.toString().contains("Message sent\nMessage sent"), is(true));
        ss.close();
    }

}