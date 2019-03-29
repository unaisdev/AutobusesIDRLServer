import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable {

    public static List<Connection> clientsConnection = new ArrayList<Connection>();
    private static final int PORT_NUMBER = 7979;
    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(PORT_NUMBER);
            System.out.println("---- SERVER ON ----");
            while(true){
                Socket clientSocket = serverSocket.accept();

                System.out.println("---- Socket recogido ----");

                clientsConnection.add(new Connection(clientSocket));
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORT_NUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public static void broadcast(GeoPoint punto, Autobus autobus){
        for (Connection conexionCli : Server.clientsConnection) {
            try {
                DataOutputStream remoteOut = new DataOutputStream(conexionCli.getSocket().getOutputStream());
                System.out.println(autobus.getNombre() + " | " + punto.getLatitude() + ", " + punto.getLongitude());
                remoteOut.writeUTF(autobus.getNombre() + " | " + punto.getLatitude() + ", " + punto.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
