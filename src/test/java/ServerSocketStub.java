import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketStub extends ServerSocket {

  private Socket socket;
  private boolean called = false;

  public ServerSocketStub() throws IOException {
  }

  public void createSocket(Socket socket) {
    this.socket = socket;
  }

  @Override
  public Socket accept(){
    called = true;
    return this.socket;
  }

  public boolean hasAcceptBeenCalled(){
    return called;
  }

}
