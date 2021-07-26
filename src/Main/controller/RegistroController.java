package Main.controller;

import Main.Model.Conexion;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class RegistroController {
    String nombreUsuario;
    @FXML
    private TextField nameUsuario;

    @FXML
    void IniciarOnMouseClicked(MouseEvent event) {

        nombreUsuario = nameUsuario.getText();
        Conexion conexion = new Conexion();
        conexion.setUsername(nombreUsuario);
        conexion.setIP("localhost");
        conexion.setPuerto(3001);
        Main.Main.setFXML("root","Whatchat");
    }


}
