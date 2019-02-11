package Server;

import Sockets.ServerSocketStub;
import Sockets.SocketStub;
import StreamSocket.StreamSocket;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListenerTest {

    private Listener listener;
    private ServerSocketStub sstub;

    @Before
    public void setUp() throws IOException {

        String mockInput = "Hello";
        ByteArrayInputStream input = new ByteArrayInputStream(mockInput.getBytes());

        ServerIO si = new ServerIO(new PrintStream(new ByteArrayOutputStream()));

        Socket socketStub = new SocketStub(input,  new PrintStream(new ByteArrayOutputStream()));
        sstub = new ServerSocketStub(socketStub);
        listener = new Listener(si, sstub);

    }

    @Test
    public void createsAStreamSocket() throws IOException {
        StreamSocket sc = listener.connect();
        assertThat(sc.readFromSocket(), is("Hello"));
    }

    @Test
    public void acceptHasBeenCalled() throws IOException {
        listener.connect();
        MatcherAssert.assertThat(sstub.hasAcceptBeenCalled(), is(true));
    }

}