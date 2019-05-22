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

    ListCell<Label> buttonCell;

    public AlertAndroid(){}

    @FXML
    private void initialize()
    {
        buttonCell = new ListCell<Label>() {
            @Override protected void updateItem(Label item, boolean isEmpty) {
                super.updateItem(item, isEmpty);
                setText(item == null ? "" : item.getText());
            }
        };
        cBTipo.setButtonCell(buttonCell);

        cBTipo.getSelectionModel().select(0);


    }

    @FXML
    private void mandarAlerta(){
        String tipo = "";

        if(cBTipo.getSelectionModel().isSelected(0))
            tipo = "Accidente";
        else if(cBTipo.getSelectionModel().isSelected(1))
            tipo = "Desviacion";
        else if(cBTipo.getSelectionModel().isSelected(2))
            tipo = "Retraso";
        else if(cBTipo.getSelectionModel().isSelected(3))
            tipo = "Averia";

        Connection.mandarAlerta(tipo, tBTitulo.getText(), tADesc.getText());
    }



}
