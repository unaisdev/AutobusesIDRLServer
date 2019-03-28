import java.io.*;
import java.lang.management.ThreadMXBean;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection implements Runnable {

    private static Socket socket;
    private String clientMsg = "";

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
                            while (!(msg = dataInputStream.readUTF()).equals("")) {
                                //Guardamos lo que nos dice el cliente en variable global

                                /* AQUÍ ES DONDE DEBEREMOS CONTROLAR LO QUE NOS LLEGA DESDE EL CLIENTE,
                                 * QUE NOS PEDIRÁ: PARADAS, RUTAS, Y AUTOBUSES.
                                 * Y deberemos de crear un nuevo hilo para que se ejecute en segundo plano el envio de
                                 * coordenadas a los clientes.
                                 *
                                 * EL HILO QUE SE ENCARGUE DE ENVIAR MENSAJES, debe ser accionado por un boton en la interfaz JavaFX
                                 * */

                                clientMsg = msg;
                                System.out.println("MENSAJE ->  " + clientMsg);
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

    @Override
    public void run() {
        connectClient();
    }
}
