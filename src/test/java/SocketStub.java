import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    private InputStream input;
    private OutputStream output;

    public SocketStub(InputStream input, OutputStream output){
        this.input = input;
        this.output = output;
    }

    @Override
    public InputStream getInputStream(){
        return input;
    }

    @Override
    public OutputStream getOutputStream(){
        return output;
    }

}
