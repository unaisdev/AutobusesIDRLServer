import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.json.*;

public class Linea {
    private String id;
    private String nombre;
    private int color;
    private JSONArray horarios;
    public ArrayList<Parada> paradas;
    public ArrayList<GeoPoint> puntosRuta;
    private JSONObject fileJson;
    private String jsonText;

    public Linea(String nombre) {
        paradas = new ArrayList<>();
        puntosRuta = new ArrayList<>();
        String stringFile = "";

        try{
            File file = new File("C:/Users/Unai Canales/Desktop/AutobusesIDRLServer/rutes/" + nombre + ".json");

            BufferedReader br = new BufferedReader(new FileReader(file));

            String auxString;
            while ((auxString = br.readLine()) != null)
                stringFile += auxString + "\n";

        }catch(IOException e){
            e.printStackTrace();
        }

        boolean error = false;
        try{
            System.out.println(stringFile);
            jsonText = stringFile;
            fileJson = new JSONObject(stringFile);
        }catch(Exception e){
            error = true;
            System.out.println("EL NOMBRE DE LA LINEA QUE SE ESTA PROPORCIONANDO NO CORRESPONDE CON NINGUN JSON.");
            e.printStackTrace();
        }

        if(!error){
            //Recogemos el nombre desde el JSON
            this.nombre = fileJson.getJSONObject("ruta").getString("idRuta");

            horarios = fileJson.getJSONArray("horario");
            for (int i = 0; i < horarios.length(); i++)
            {
                System.out.println(horarios.getString(i));
            }

            //Recogemos los objetos del Array que son CADA PUNTO de la ruta y lo guardamos en el ArrayList
            JSONArray ruta = fileJson.getJSONObject("ruta").getJSONArray("puntos");

            for (int i = 0; i < ruta.length(); i++)
            {
                double latitude = ruta.getJSONObject(i).getDouble("_lat");
                double longitude = ruta.getJSONObject(i).getDouble("_lon");

                GeoPoint nuevoPunto = new GeoPoint(latitude, longitude);
                puntosRuta.add(nuevoPunto);
            }

            JSONArray arrParada = fileJson.getJSONArray("paradas");

            for (int i = 0; i < arrParada.length(); i++)
            {
                String nombreParada = arrParada.getJSONObject(i).getString("name");
                ArrayList<String> lineasAsociadas = new ArrayList<>();
                JSONArray lineasAsoc = arrParada.getJSONObject(i).getJSONArray("lines");

                //Recogemos las lineas que tiene asociadas cada parada
                for (int h = 0; h < lineasAsoc.length(); h++)
                    lineasAsociadas.add(lineasAsoc.getString(h));

                double latitude = arrParada.getJSONObject(i).getDouble("_lat");
                double longitude = arrParada.getJSONObject(i).getDouble("_lon");

                GeoPoint nuevoPunto = new GeoPoint(latitude, longitude);

                Parada nuevaParada = new Parada(nuevoPunto, nombreParada, lineasAsociadas);

                paradas.add(nuevaParada);
            }

        }

    }

    public String getJsonText() {
        return jsonText;
    }

    public void setJsonText(String jsonText) {
        this.jsonText = jsonText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public JSONArray getHorarios() {
        return horarios;
    }

    public void setHorarios(JSONArray horarios) {
        this.horarios = horarios;
    }

    public ArrayList<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(ArrayList<Parada> paradas) {
        this.paradas = paradas;
    }

    public JSONObject getFileJson() {
        return fileJson;
    }

    public void setFileJson(JSONObject fileJson) {
        this.fileJson = fileJson;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<GeoPoint> getPuntosRuta() {
        return puntosRuta;
    }

    public void setPuntosRuta(ArrayList<GeoPoint> puntosRuta) {
        this.puntosRuta = puntosRuta;
    }

    @Override
    public String toString() {
        return "Linea [id=" + id + ", nombre=" + nombre + ", "
                + "]";
    }
}
