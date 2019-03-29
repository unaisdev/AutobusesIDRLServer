import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class MainController {

    private static ArrayList<Linea> lineas = new ArrayList<Linea>();

    @FXML
    private ToggleGroup tGLineas;

    @FXML
    private ToggleGroup tGAutobuses;

    @FXML
    private ToggleButton tBLineaH25;

    @FXML
    private ToggleButton tBLineaA15;

    @FXML
    private ToggleButton tBAutobusCHR;

    @FXML
    private ToggleButton tBAutobusKLR;

    public MainController(){}

    @FXML
    private void initialize()
    {
        tBLineaH25.setToggleGroup(tGLineas);
        tBLineaH25.setUserData("H - 25");
        tBLineaA15.setToggleGroup(tGLineas);
        tBLineaA15.setUserData("A - 15");
        tBAutobusCHR.setToggleGroup(tGAutobuses);
        tBAutobusCHR.setUserData("AutobusCHR");
        tBAutobusKLR.setToggleGroup(tGAutobuses);
        tBAutobusKLR.setUserData("AutobusKLR");

        tGLineas.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (tGLineas.getSelectedToggle() != null) {

                    System.out.println(tGLineas.getSelectedToggle().getUserData().toString());
                    // Do something here with the userData of newly selected radioButton

                }

            }
        });

        tGAutobuses.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (tGAutobuses.getSelectedToggle() != null) {

                    System.out.println(tGAutobuses.getSelectedToggle().getUserData().toString());
                    // Do something here with the userData of newly selected radioButton

                }

            }
        });
    }

    @FXML
    private void lanzarAutobus()
    {
        System.out.println("BOTON PULSAO");

        for (Linea linea: Main.lineasUp) {
            if(linea.getNombre().equals(tGLineas.getSelectedToggle().getUserData().toString())){
                for (Autobus autobus: Main.autobusesUp) {
                    if(autobus.getNombre().equals(tGAutobuses.getSelectedToggle().getUserData().toString())){
                        new Thread(new SimulacionBus(linea, autobus)).start();
                    }else{
                        //TODO: TRATAMOS CUANDO EL AUTOBUS NO EXISTE EN EL ARRAY

                    }
                }
            }else{
                //TODO: TRATAMOS CUANDO LA LINEA NO EXISTE EN EL ARRAY
            }
        }
    }

    private String getSelectedLineaAutobus(){
        String selected = "";

        if(tBLineaA15.isSelected()){
            selected = "LINEA: " + tBLineaA15.getUserData();
        }else if(tBLineaH25.isSelected()){
            selected = "LINEA: " + tBLineaH25.getText();
        }

        selected = selected + " | ";

        if(tBAutobusKLR.isSelected()){
            selected = selected + tBAutobusKLR.getText();
        }else if(tBAutobusCHR.isSelected()){
            selected = selected + tBAutobusCHR.getText();
        }

        return selected;
    }
}
