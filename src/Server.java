import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private static final int PORT_NUMBER = 7979;
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("---- SERVER ON ----");
            while(true){
                Socket clientSocket = serverSocket.accept();

                DataOutputStream remoteOut = new DataOutputStream(clientSocket.getOutputStream());
                MainController.clients.add(remoteOut);
                System.out.println("---- Socket recogido ----");

                //new Thread(new Connection(clientSocket)).start();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORT_NUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
