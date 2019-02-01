import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StreamSocketTest {

    private Socket clientSocket;
    private StreamSocket streamSocket;
    private PrintStream output;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream out;

    @Before
    public void setUp() throws IOException {

        out = new ByteArrayOutputStream();
        output = new PrintStream(out);

        String mockInput = "Hello";
        input = new ByteArrayInputStream(mockInput.getBytes());

        clientSocket = new SocketStub(input, output);
        streamSocket = new StreamSocket(clientSocket);

    }

    @Test
    public void readsLineFromSocket() throws IOException {
        assertThat(streamSocket.readFromSocket(), is("Hello"));
    }

    @Test
    public void writesToSocket() {
        streamSocket.printToSocket("Hello");
        assertThat(out.toString().trim(), is("Hello"));
    }

    @Test
    public void hasMessageBeenSent(){
        streamSocket.printToSocket("Hello");
        assertThat(streamSocket.isMessageSent(), is(true));
    }
}
