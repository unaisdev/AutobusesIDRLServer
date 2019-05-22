import java.io.*;
import java.lang.management.ThreadMXBean;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class Connection implements Runnable {

    private Socket socket;
    private String msgCli = "";

    public Connection(Socket socket){
        this.socket = socket;
    }

    public void connectClient() {
        try{

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while (true){
                            String msg;
                            while (
                                    (msg = dataInputStream.readUTF()).equals("")) {
                                //Guardamos lo que nos dice el cliente en variable global

                                /* AQUÍ ES DONDE DEBEREMOS CONTROLAR LO QUE NOS LLEGA DESDE EL CLIENTE,
                                 * AUNQUE EN ESTE CASO NO DEBEREMOS DE UTILIZAR
                                 *
                                 * */

                                msgCli = msg;
                                System.out.println("MENSAJE ->  " + msgCli);
                            }
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
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
        connectClient();
    }

    public void sendToNewClientAutobuses(){
        for (Autobus autobus : Main.autobusesUp) {
            try {
                DataOutputStream remoteOut = new DataOutputStream(this.socket.getOutputStream());
                GeoPoint punto = autobus.getPunto();
                remoteOut.writeUTF("posBus:" + autobus.getNombre() + "| " + punto.getLatitude() + ", " + punto.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToNewClientLineas(){
        for (Linea linea : Main.lineasUp) {
            try {
                DataOutputStream remoteOut = new DataOutputStream(this.socket.getOutputStream());

                remoteOut.writeUTF("linea: " + linea.getJsonText());
                System.out.println("linea: " + linea.getJsonText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendToNewClientParadas(){
        for (Linea linea : Main.lineasUp) {
            for(Parada parada: linea.getParadas()){
                try {
                    DataOutputStream remoteOut = new DataOutputStream(this.socket.getOutputStream());
                    remoteOut.writeUTF(parada.imprimirParada());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

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

    public static void mandarAlerta(String tipo, String titulo, String desc){
        for (Connection conexionCli : Server.clientsConnection) {
            try {
                DataOutputStream remoteOut = new DataOutputStream(conexionCli.getSocket().getOutputStream());
                System.out.println("alerta:" + tipo + titulo + "," + desc);
                remoteOut.writeUTF("alerta:" + tipo + ", " + titulo + "," + desc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
