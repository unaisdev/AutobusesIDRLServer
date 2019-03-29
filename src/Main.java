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
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        Linea lineaA15 = new Linea("LineaA-15");
        lineasUp.add(lineaA15);
        Linea lineaH25 = new Linea("LineaH-25");
        lineasUp.add(lineaH25);

        Autobus autobusCHR = new Autobus("AutobusCHR", new GeoPoint(43.2456, 23.45559292), "infodrmacionnn");
        autobusesUp.add(autobusCHR);
        Autobus autobusKLR = new Autobus("AutobusKLR", new GeoPoint(232.23123123, 112.394701), "infodrmacionnn");
        autobusesUp.add(autobusKLR);

        Scene scene = new Scene(root);

        primaryStage.setTitle("Autobuses IDRL");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
