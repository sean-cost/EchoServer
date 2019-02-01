import java.io.*;
import java.net.Socket;

public class StreamSocket {
    private BufferedReader in;
    private PrintWriter out;
    private boolean messageSent = false;

    public StreamSocket(Socket sc) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
        this.out = new PrintWriter(sc.getOutputStream(), true);
    }

    public boolean isMessageSent() {
        return messageSent;
    }

    public String readFromSocket() throws IOException {
        return in.readLine();
    }

    public void printToSocket(String message){
        out.println(message);
        messageSent = !out.checkError();
    }

}
