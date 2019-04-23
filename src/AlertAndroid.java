import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AlertAndroid {

    @FXML
    private ComboBox cBTipo;

    @FXML
    private TextField tBTitulo;

    @FXML
    private TextArea tADesc;

    public AlertAndroid(){}

    @FXML
    private void initialize()
    {
        String[] tipoAlertas = {"Accidente", "Desviación", "Retraso", "Avería"};
        ObservableList<String> observableList = cBTipo.getItems();
        observableList.addAll(tipoAlertas);

        cBTipo.getSelectionModel().select("Accidente");
    }

    @FXML
    private void mandarAlerta(){
        Server.mandarAlerta(cBTipo.getAccessibleText(), tBTitulo.getText(), tADesc.getText());
    }



}
