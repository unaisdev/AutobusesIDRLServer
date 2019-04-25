import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {

    public static ArrayList<Linea> lineasUp = new ArrayList<>();
    public static ArrayList<Autobus> autobusesUp = new ArrayList<>();

    public static void main(String[] args) {

        Server servidor = new Server();
        new Thread(servidor).start();

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

        //Linea lineaA15 = new Linea("LineaA-15");
        //lineasUp.add(lineaA15);
        Linea lineaH25 = new Linea("LineaH-25_copy");
        lineasUp.add(lineaH25);

        Autobus autobusCHR = new Autobus("AutobusCHR", new GeoPoint(43.339814, -1.77657), "infodrmacionnn", false);
        autobusesUp.add(autobusCHR);
        Autobus autobusKLR = new Autobus("AutobusKLR", new GeoPoint(43.379814, -1.68657), "infodrmacionnn", false);
        autobusesUp.add(autobusKLR);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Autobuses IDRL");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
