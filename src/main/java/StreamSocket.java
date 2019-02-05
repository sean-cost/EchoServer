import java.io.*;
import java.net.Socket;

public class StreamSocket extends Socket {
    private BufferedReader in;
    private PrintWriter out;
    private boolean messageSent = false;

    public StreamSocket(InputStream inputStream, OutputStream outputStream) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
        this.out = new PrintWriter(outputStream, true);
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

    public void closeConnection() throws IOException {
        this.in.close();
        this.out.close();
        this.close();
    }

}
