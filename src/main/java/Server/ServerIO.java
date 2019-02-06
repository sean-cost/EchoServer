package Server;

import java.io.PrintStream;

public class ServerIO {
    private PrintStream output;

    public ServerIO(PrintStream output){
        this.output = output;
    }

    public void inform(String input){
       output.println(input);
    }
}
