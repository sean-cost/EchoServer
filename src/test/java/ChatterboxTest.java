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

        new Thread(() -> {
            try {
                ServerInterface si = new ServerInterface(new PrintStream(out));
                Chatterbox server = new Chatterbox(new Listener(si, new ServerSocket(9090)), si);
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Socket socket = new Socket("localhost", 9090);
        socket.getOutputStream().write("Hello\n".getBytes());

        Thread.sleep(50);

        assertThat(out.toString().contains("Message sent"), is(true));
    }

}