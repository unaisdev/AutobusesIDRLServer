import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {

        Server servidor = new Server();
        new Thread(servidor).start();

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Autobuses IDRL");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
