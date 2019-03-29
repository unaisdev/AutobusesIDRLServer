public class Autobus {
    private String nombre;
    private GeoPoint punto;
    private String info;

    public Autobus(String nombre, GeoPoint punto, String info) {
        this.nombre = nombre;
        this.punto = punto;
        this.info = info;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public GeoPoint getPunto() {
        return punto;
    }

    public void setPunto(GeoPoint punto) {
        this.punto = punto;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
