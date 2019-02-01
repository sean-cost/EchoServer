import java.io.PrintStream;

public class UserInterface {
    private PrintStream output;

    public UserInterface(PrintStream output){
        this.output = output;
    }

    public void inform(String input){
       output.println(input);
    }
}
