package Main.controller;

import Main.Model.Conexion;
import Main.Model.ThreadClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class Controller implements Observer, Initializable {
    private Socket socket;
    private DataOutputStream bufferDeSalida = null;

    @FXML
    private TextField mensaje;

    @FXML
    private TextArea areaChat;

    @FXML
    private ComboBox<String> chats;

    @FXML
    void enviarOnMouseClicked(MouseEvent event) throws IOException {
        Conexion conexion = new Conexion();
        if(chats.getValue()==null){
            System.out.println("No se ha seleccionado ningun destinatario");
        }else{
            bufferDeSalida.writeUTF(conexion.getUsername()+":"+chats.getValue()+":"+mensaje.getText());
            bufferDeSalida.flush();
        }
    }

    public void iniciar(){
        Conexion conexion = new Conexion();
        try{
            socket = new Socket(conexion.getIP(),conexion.getPuerto());
            bufferDeSalida = new DataOutputStream(socket.getOutputStream());
            bufferDeSalida.writeUTF(conexion.getUsername());
            bufferDeSalida.flush();
            ThreadClient cliente = new ThreadClient(socket,areaChat,chats);
            cliente.addObserver(this);
            new Thread(cliente).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        String Resp = (String) arg;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        iniciar();
    }
}

