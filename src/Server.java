import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

import javax.xml.crypto.Data;


public class Server implements Runnable {

    public static List<Connection> clientsConnection = new ArrayList<Connection>();
    private static final int PORT_NUMBER = 7979;
    private static ServerSocket serverSocket;

    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("---- SERVER ON ----");
            while(true){
                Socket clientSocket = serverSocket.accept();

                System.out.println("---- Socket recogido ----");

                Connection conn = new Connection(clientSocket);
                clientsConnection.add(conn);
                new Thread(conn).start();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORT_NUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
