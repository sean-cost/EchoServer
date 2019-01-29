import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketStub extends ServerSocket {

  private Socket socketConnector;

  public ServerSocketStub() throws IOException {
  }

  public void createSocket(Socket socketConnector) {
    this.socketConnector = socketConnector;
  }

  @Override
  public Socket accept(){
    return this.socketConnector;
  }

}
