import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EchoThreadTest {

    private EchoThread echoThread;
    private ByteArrayOutputStream out;
    private ByteArrayOutputStream stout;
    private StreamSocket sc;

    @Before
    public void setUp() throws IOException {
        //client console
        out = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(out);

        String mockInput = "Hello\nWorld";
        ByteArrayInputStream input = new ByteArrayInputStream(mockInput.getBytes());

        //server console
        stout = new ByteArrayOutputStream();
        ServerInterface si = new ServerInterface(new PrintStream(stout));

        sc = new StreamSocket(input, output);

        echoThread = new EchoThread(sc, si);

    }

    @Test
    public void sendsMessagesback() {
        echoThread.run();
        assertThat(out.toString().trim().contains("Hello\nWorld"), is(true));
    }

    @Test
    public void verifiesMessageSent() {
        echoThread.run();
        assertThat(stout.toString().contains("Message sent"), is(true));
    }

    @Test
    public void sendsInstructionsToClient() {
        echoThread.sendInstructions("Hello! Please insert a word");
        assertThat(out.toString().trim(), is("Hello! Please insert a word"));
    }

}