import java.io.IOException;

public class EchoThread extends Thread{

    private StreamSocket sc;
    private ServerInterface si;

    EchoThread(StreamSocket sc, ServerInterface si){
        this.sc = sc;
        this.si = si;
    }

    public void sendInstructions(String message) {
        sc.printToSocket(message);
    }

    public void run() {
        sendInstructions("Hello! Please insert a word");
        String message;

        while (true){
            try {
                message = sc.readFromSocket();
                if ((message == null) || message.equalsIgnoreCase("QUIT")){
                    sc.closeConnection();
                    return;
                }else{
                    sc.printToSocket(message);
                    if(sc.isMessageSent()){
                        si.inform("Message sent");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
