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
                Server.broadcastAutobus(punto, autobus);
                Thread.sleep(1500);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void mandarAutobuses(){
        for (Autobus autobus: Main.autobusesUp) {

        }
    }

    @Override
    public void run() {
        lanzarAutobus();
    }
}
