import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteNuevo {
    private static final int PORT_NUMBER = 7979;
    private static final String HOSTNAME = "localhost";
    private static DataOutputStream remoteOut;
    private static DataInputStream remoteIn;


    public static void main(String[] args) throws IOException {
        try{
            Socket echoSocket = new Socket(HOSTNAME, PORT_NUMBER);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            remoteIn = new DataInputStream(echoSocket.getInputStream());

            String userInput;
            while((userInput =remoteIn.readUTF())!=null)

            {
                System.out.println("echo: " + remoteIn.readLine());
            }
        } catch (  UnknownHostException e) {
            System.err.println("Don't know about host " + HOSTNAME);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    HOSTNAME);
            System.exit(1);
        }
    }
}
