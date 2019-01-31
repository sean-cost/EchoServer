import java.io.*;
import java.net.Socket;

public class StreamSocket {
    private BufferedReader in;
    private PrintWriter out;

    public StreamSocket(Socket sc) throws IOException {
        createInOut(sc.getInputStream(), sc.getOutputStream());
    }

    private void createInOut(InputStream in, OutputStream out){
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintWriter(out, true);
    }

    public String readFromSocket() throws IOException {
        return in.readLine();
    }

    public void printToSocket(String message){
        out.println(message);
    }
}
