import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Connection implements Runnable {

    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private Socket socket;
    private String tipo;
    private String msgCli = "";

    public Connection(Socket socket){
        this.socket = socket;
    }

    public void connectClient() {
        try{

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            sendToNewClientLineas();

            try{
                while (true){
                    String msg;
                    while (!(msg = dataInputStream.readUTF()).equals("")) {
                        //Guardamos lo que nos dice el cliente en variable global

                        /* AQUÍ ES DONDE DEBEREMOS CONTROLAR LO QUE NOS LLEGA DESDE EL CLIENTE,
                         * AUNQUE EN ESTE CASO NO DEBEREMOS DE UTILIZAR
                         *
                         * */

                        switch(findForWhat(msg)){

                            case "movBus":
                                mandarBroadcast(msg);
                                break;

                        }

                        msgCli = msg;
                        System.out.println("MENSAJE ->  " + msgCli);
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }

        }catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        }
    }

    //Metodo que se encarga de ver que tipo de mensaje llega desde el servidor
    public static String findForWhat(String msg){
        return msg.substring(0, msg.indexOf(':'));
    }

    public Socket getSocket() {
        return socket;
    }


    @Override
    public void run() {
        connectClient();
    }


    public void sendToNewClientLineas() throws IOException {
        for (Linea linea : Main.lineasUp) {
            dataOutputStream.writeUTF("linea: " + linea.getJsonText());
            for(Parada parada: linea.getParadas()){
                try {
                    dataOutputStream.writeUTF(parada.imprimirParada());
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void mandarBroadcast(String msg){
        for (Connection conexionCli : Server.clientsConnection) {
            try {
                DataOutputStream remoteOut = new DataOutputStream(conexionCli.getSocket().getOutputStream());
                remoteOut.writeUTF(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
