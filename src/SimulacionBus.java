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
                Connection.broadcastAutobus(punto, autobus);
                Thread.sleep(500);
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
