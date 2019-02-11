package Client;

import Server.Chatterbox;
import Server.Listener;
import Server.ServerIO;
import Sockets.SocketStub;
import StreamSocket.StreamSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BabblerTest {
    private Babbler babbler;
    private ByteArrayOutputStream stdOut;
    private PrintStream stdOutput;
    private StreamSocket ss;
    private ServerSocket serverSocket;

    private ClientIO mockClientIO(String stdInput) {
        return new ClientIO(new ByteArrayInputStream(stdInput.getBytes()), stdOutput);
    }

    @Before
    public void setUp() {
        stdOut = new ByteArrayOutputStream();
        stdOutput = new PrintStream(stdOut);
    }

    @Test
    public void sendsAMessageToServerSocket() {
        InputStream input = new ByteArrayInputStream("".getBytes());
        OutputStream output = new ByteArrayOutputStream();
        ss = new StreamSocket(input, output);
        babbler = new Babbler(ss, mockClientIO(""));

        babbler.send("This is my message");

        assertThat(output.toString().trim(), is("This is my message"));
    }

    @Test
    public void receivesAMessageFromServerSocket() throws IOException {
        String mockInput = "This is my message";
        InputStream socketInput = new ByteArrayInputStream(mockInput.getBytes());
        SocketStub socket = new SocketStub(socketInput, new ByteArrayOutputStream());

        ss = new StreamSocket(socket.getInputStream(), socket.getOutputStream());
        babbler = new Babbler(ss, mockClientIO(""));

        babbler.receive();

        assertThat(stdOut.toString().trim(), is("This is my message"));
    }

    @Test
    public void stopsAThread() throws IOException {
        serverSocket = new ServerSocket(5002);
        new Thread(() -> {
            try {
                ServerIO si = new ServerIO(new PrintStream(new ByteArrayOutputStream()));
                Chatterbox server = new Chatterbox(new Listener(si, serverSocket), si);
                server.start();
            } catch (IOException  e){}
        }).start();

        ClientIO ci = mockClientIO("Merce\nquit");
        Socket socket = new Socket("localhost", 5002);
        ss = new StreamSocket(socket.getInputStream(), socket.getOutputStream());
        babbler = new Babbler(ss, ci);

        babbler.introduceYourself();

        assertThat(ss.isClosed(), is(true));

        serverSocket.close();
    }

    @Test
    public void canSendAndReceiveMultipleMessages() throws IOException {
        serverSocket = new ServerSocket(5002);
        new Thread(() -> {
            try {
                ServerIO si = new ServerIO(new PrintStream(new ByteArrayOutputStream()));
                Chatterbox server = new Chatterbox(new Listener(si, serverSocket), si);
                server.start();
            } catch (IOException  e){}
        }).start();

        ClientIO ci = mockClientIO("Merce\nThis is my message\nAnd this one too\nquit");
        Socket socket = new Socket("localhost", 5002);
        ss = new StreamSocket(socket.getInputStream(), socket.getOutputStream());
        babbler = new Babbler(ss, ci);

        babbler.introduceYourself();

        assertThat(stdOut.toString().contains("This is my message\nAnd this one too"), is(true));

        serverSocket.close();
    }

}