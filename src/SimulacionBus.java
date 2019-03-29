import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SimulacionBus implements Runnable{

    private Linea linea;
    private Autobus autobus;

    public SimulacionBus(Linea linea, Autobus autobus){
        this.autobus = autobus;
        this.linea = linea;
    }

    public void lanzarAutobus() {
        try {
            for (GeoPoint punto : linea.getPuntosRuta()) {
                Server.broadcast(punto, autobus);
                Thread.sleep(500);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        lanzarAutobus();
    }
}
