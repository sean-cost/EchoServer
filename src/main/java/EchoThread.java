import java.io.IOException;

public class EchoThread extends Thread{

    private StreamSocket sc;
    private ServerInterface si;
    private boolean isStopRequested;

    EchoThread(StreamSocket sc, ServerInterface si){
        this.sc = sc;
        this.si = si;
    }

    public void sendInstructions(String message) {
        sc.printToSocket(message);
    }

    @Override
    public void run() {
        sendInstructions("Hello! Please insert a word");
        String message;

        while (!isStopRequested()){
            try {
                message = sc.readFromSocket();
                if ((message == null) || message.equalsIgnoreCase("QUIT")){
                    sc.closeConnection();
                    requestStop();
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

    public synchronized void requestStop(){
        isStopRequested = true;
    }

    private synchronized boolean isStopRequested() {
        return isStopRequested;
    }


}
