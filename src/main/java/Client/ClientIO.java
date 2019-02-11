package Client;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ClientIO {
    private PrintStream output;
    private Scanner input;

    public ClientIO(InputStream inputStream, PrintStream output){
        this.input = new Scanner(inputStream);
        this.output = output;
    }

    public void inform(String input){
        output.println(input);
    }

    public String getInput(){
        return input.nextLine();
    }
}
