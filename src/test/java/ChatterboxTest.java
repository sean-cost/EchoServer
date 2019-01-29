import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.net.Socket;


public class ChatterboxTest {

    Socket clientSocket;
    ServerSocketStub ssStub;
    Chatterbox server;

    @Before
    public void setUp() throws IOException {
        clientSocket = new Socket();
        ssStub = new ServerSocketStub();

        ssStub.createSocket(clientSocket);
        server = new Chatterbox(ssStub);
    }

    @Test
    public void acceptsASocket() throws IOException{
        server.listen();
        assertThat(server.getSocketConnector(), is(clientSocket));
    }

}