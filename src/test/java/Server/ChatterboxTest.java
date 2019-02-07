package Server;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatterboxTest {
    private ServerSocket ss;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() throws Exception {
        out = new ByteArrayOutputStream();
        ss = new ServerSocket(5001);

        new Thread(() -> {
            try {
                ServerIO si = new ServerIO(new PrintStream(out));
                Chatterbox server = new Chatterbox(new Listener(si, ss), si);
                server.start();
            } catch (IOException  e){}
        }).start();
    }

    @Test
    public void theServerEchoesAMessage() throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 5001);
        socket.getOutputStream().write("Hello\n".getBytes());

        Thread.sleep(50);

        assertThat(out.toString().contains("Message sent"), is(true));
    }

    @Test
    public void theServerAllowsMultipleClients() throws IOException, InterruptedException {
        Socket oneSocket = new Socket("localhost", 5001);
        oneSocket.getOutputStream().write("Hello world\n".getBytes());

        Socket twoSocket = new Socket("localhost", 5001);
        twoSocket.getOutputStream().write("Hello again\n".getBytes());

        Thread.sleep(50);
        System.out.println(out.toString());

        assertThat(out.toString().contains("Message sent\nMessage sent"), is(true));
    }

    @After
    public void tearDown() throws Exception {
        ss.close();
    }
}