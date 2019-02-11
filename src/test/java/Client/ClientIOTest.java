package Client;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClientIOTest {
    private ByteArrayOutputStream out;
    private PrintStream output;
    private ClientIO ci;

    private ClientIO mockClientIO(String mockInput) {
        ByteArrayInputStream input = new ByteArrayInputStream(mockInput.getBytes());

        return new ClientIO(input, output);
    }

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        output = new PrintStream(out);
    }

    @Test
    public void asksUserForText(){
        ci = mockClientIO("I want to echo this sentence");
        assertThat (ci.getInput(), is("I want to echo this sentence"));
    }

    @Test
    public void informsUserEchoedWord(){
        ci = mockClientIO("");
        String message = "Message to be sent";
        ci.inform(message);
        assertThat (out.toString().trim(), is("Message to be sent"));
    }

}