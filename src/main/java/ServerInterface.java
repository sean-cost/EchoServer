import java.io.PrintStream;

public class ServerInterface {
    private PrintStream output;

    public ServerInterface(PrintStream output){
        this.output = output;
    }

    public void inform(String input){
       output.println(input);
    }
}
