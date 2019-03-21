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

            InputStream inputStream = socket.getInputStream();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

            // Input
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        while (true){
                            String aux;
                            while ((aux = bufferedReader.readLine()) != null) {
                                //Guardamos lo que nos dice el cliente en variable global
                                clientMsg = aux;
                                System.out.println("Cliente -> " + aux);
                            }
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            // Ouuput
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 0;
                    while (true) {
                        i++;
                        try {
                            try {
                                bufferedWriter.write(clientMsg + "\n");
                                bufferedWriter.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
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
