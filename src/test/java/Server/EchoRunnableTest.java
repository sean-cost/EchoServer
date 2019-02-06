package Server;

import StreamSocket.StreamSocket;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EchoRunnableTest {

    private ByteArrayOutputStream out;
    private ByteArrayOutputStream stout;
    private StreamSocket sc;
    private PrintStream output;
    private ServerIO si;

    @Before
    public void setUp() {
        //client console
        out = new ByteArrayOutputStream();
        output = new PrintStream(out);

        //server console
        stout = new ByteArrayOutputStream();
        si = new ServerIO(new PrintStream(stout));

    }

    @Test
    public void sendsMultipleMessagesBack() {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello\nWorld".getBytes());

        sc = new StreamSocket(input, output);
        new EchoRunnable(sc, si).run();

        assertThat(out.toString().trim().contains("Hello\nWorld"), is(true));
    }

    @Test
    public void verifiesMessageSent() {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello\nWorld".getBytes());

        sc = new StreamSocket(input, output);
        new EchoRunnable(sc, si).run();

        assertThat(stout.toString().contains("Message sent"), is(true));
    }

    @Test
    public void sendsInstructionsToClient() {
        sc = new StreamSocket(new ByteArrayInputStream("\n".getBytes()), output);
        EchoRunnable echoRunnable = new EchoRunnable(sc, si);

        echoRunnable.sendInstructions("Hello! Please insert a word");
        assertThat(out.toString().trim(), is("Hello! Please insert a word"));
    }

    @Test
    public void closesASocket() {
        String mockInput = "quit\n";
        ByteArrayInputStream input = new ByteArrayInputStream(mockInput.getBytes());

        sc = new StreamSocket(input, output);
        new EchoRunnable(sc, si).run();

        assertThat(sc.isClosed(), is(true));
    }

    @Test
    public void stopsAThread() {
        String mockInput = "quit\n";
        ByteArrayInputStream input = new ByteArrayInputStream(mockInput.getBytes());

        sc = new StreamSocket(input, output);
        EchoRunnable echo = new EchoRunnable(sc, si);
        echo.run();

        assertThat(echo.isStopRequested(), is(true));
    }


}