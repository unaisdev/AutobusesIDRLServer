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

                clientsConnection.add(new Connection(clientSocket));
                new Thread(new SendAutobuses(clientSocket)).start();
                new Thread(new SendParadas(clientSocket)).start();
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORT_NUMBER + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }


    public static void broadcastAutobus(GeoPoint punto, Autobus autobus){
        for (Connection conexionCli : Server.clientsConnection) {
            try {
                DataOutputStream remoteOut = new DataOutputStream(conexionCli.getSocket().getOutputStream());
                System.out.println("movBus:" + autobus.getNombre() + " | " + punto.getLatitude() + ", " + punto.getLongitude());
                remoteOut.writeUTF("movBus:" + autobus.getNombre() + "| " + punto.getLatitude() + ", " + punto.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class SendAutobuses implements Runnable {

        private Socket socket;
        private String msgCli = "";

        public SendAutobuses(Socket socket){
            this.socket = socket;
        }

        private void sendToNewClientAutobuses(){
            for (Autobus autobus : Main.autobusesUp) {
                try {
                    DataOutputStream remoteOut = new DataOutputStream(this.socket.getOutputStream());
                    GeoPoint punto = autobus.getPunto();
                    System.out.println("posBus:" + autobus.getNombre() + " | " + punto.getLatitude() + ", " + punto.getLongitude());
                    remoteOut.writeUTF("posBus:" + autobus.getNombre() + "| " + punto.getLatitude() + ", " + punto.getLongitude());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            socket = socket;
        }

        @Override
        public void run() {
            sendToNewClientAutobuses();
        }
    }

    public class SendLineas implements Runnable {

        private Socket socket;

        public SendLineas(Socket socket){
            this.socket = socket;
        }

        private void sendToNewClientLineas(){
            for (Linea linea : Main.lineasUp) {
                try {
                    ObjectOutputStream remoteOut = new ObjectOutputStream(this.socket.getOutputStream());

                    remoteOut.writeObject(linea.getFileJson());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            socket = socket;
        }

        @Override
        public void run() {
            sendToNewClientLineas();
        }
    }

    public class SendParadas implements Runnable {

        private Socket socket;

        public SendParadas(Socket socket){
            this.socket = socket;
        }

        private void sendToNewClientLineas(){
            for (Linea linea : Main.lineasUp) {
                for(Parada parada: linea.getParadas()){
                    try {
                        DataOutputStream remoteOut = new DataOutputStream(this.socket.getOutputStream());
                        System.out.print(parada.imprimirParada());
                        remoteOut.writeUTF(parada.imprimirParada());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            socket = socket;
        }

        @Override
        public void run() {
            sendToNewClientLineas();
        }
    }
}
