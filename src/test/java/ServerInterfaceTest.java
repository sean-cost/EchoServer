import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


public class ServerInterfaceTest {
    private ByteArrayOutputStream out;
    private PrintStream output;
    ServerInterface si;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        output = new PrintStream(out);
        si = new ServerInterface(output);
    }

    @Test
    public void informsTheServer(){
        String message = "server started";
        si.inform(message);
        assertThat(out.toString().trim(), is(message));
    }


}