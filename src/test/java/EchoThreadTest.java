import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EchoThreadTest {

    private ByteArrayOutputStream out;
    private ByteArrayOutputStream stout;
    private StreamSocket sc;
    private PrintStream output;
    private ServerInterface si;

    @Before
    public void setUp() {
        //client console
        out = new ByteArrayOutputStream();
        output = new PrintStream(out);

        //server console
        stout = new ByteArrayOutputStream();
        si = new ServerInterface(new PrintStream(stout));

    }

    @Test
    public void sendsMultipleMessagesBack() {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello\nWorld".getBytes());

        sc = new StreamSocket(input, output);
        new EchoThread(sc, si).run();

        assertThat(out.toString().trim().contains("Hello\nWorld"), is(true));
    }

    @Test
    public void verifiesMessageSent() {
        ByteArrayInputStream input = new ByteArrayInputStream("Hello\nWorld".getBytes());

        sc = new StreamSocket(input, output);
        new EchoThread(sc, si).run();

        assertThat(stout.toString().contains("Message sent"), is(true));
    }

    @Test
    public void sendsInstructionsToClient() {
        sc = new StreamSocket(new ByteArrayInputStream("\n".getBytes()), output);
        EchoThread echoThread = new EchoThread(sc, si);

        echoThread.sendInstructions("Hello! Please insert a word");
        assertThat(out.toString().trim(), is("Hello! Please insert a word"));
    }

    @Test
    public void closesASocket() {
        String mockInput = "quit\n";
        ByteArrayInputStream input = new ByteArrayInputStream(mockInput.getBytes());

        sc = new StreamSocket(input, output);
        new EchoThread(sc, si).run();

        assertThat(sc.isClosed(), is(true));
    }

    @Test
    public void stopsAThread() {
        String mockInput = "quit\n";
        ByteArrayInputStream input = new ByteArrayInputStream(mockInput.getBytes());

        sc = new StreamSocket(input, output);
        EchoThread echo = new EchoThread(sc, si);
        echo.run();

        assertThat(echo.isStopRequested(), is(true));
    }


}