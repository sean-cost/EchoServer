import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.*;
import java.net.Socket;


public class ChatterboxTest {

    private Socket clientSocket;
    private ServerSocketStub ssStub;
    private Chatterbox server;
    private PrintStream output;
    private ByteArrayInputStream input;
    private ByteArrayOutputStream out;
    private ByteArrayOutputStream stout;

    @Before
    public void setUp() throws IOException {
        //client console
        out = new ByteArrayOutputStream();
        output = new PrintStream(out);

        String mockInput = "Hello";
        input = new ByteArrayInputStream(mockInput.getBytes());

        //server console
        stout = new ByteArrayOutputStream();

        clientSocket = new SocketStub(input, output);
        ssStub = new ServerSocketStub();

        ssStub.createSocket(clientSocket);

        server = new Chatterbox(ssStub, new ServerInterface(new PrintStream(stout)));
    }

    @Test
    public void acceptsASocket() throws IOException{
        server.connect();
        assertThat(server.getSocket(), is(clientSocket));
    }

    @Test
    public void acceptsHasBeenCalled() throws IOException{
        server.connect();
        assertThat(ssStub.hasAcceptBeenCalled(), is(true));
    }

    @Test
    public void sendsAMessageBack() throws IOException {
        server.connect();
        server.echo();
        assertThat(out.toString().trim(), is("Hello"));
    }

    @Test
    public void verifiesMessageSent() throws IOException{
        server.connect();
        server.echo();
        assertThat(stout.toString().contains("Message sent"), is(true));
    }

    @Test
    public void sendsInstructionsToClient() throws IOException {
        server.connect();
        server.sendInstructions();
        assertThat(out.toString().trim(), is("Hello! Please insert a word"));
    }



}