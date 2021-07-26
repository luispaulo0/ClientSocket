
package Main.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Observable;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadClient extends Observable implements Runnable {
    private Socket socket;
    private DataInputStream bufferDeEntrada = null;
    private TextArea areaChat;
    private ComboBox<String> chats;
    boolean bandera = true;
    Mensaje mensajes = new Mensaje();
    ObservableList <String> mensajesEntrada = FXCollections.observableArrayList();
    ObservableList <Mensaje> listaMensaje = FXCollections.observableArrayList();

    public ThreadClient(Socket socket, TextArea areaChat, ComboBox chats) {
        this.socket = socket;
        this.areaChat = areaChat;
        this.chats = chats;
    }

    public void run() {

        try {
            bufferDeEntrada = new DataInputStream(socket.getInputStream());

            String st = "";
            do {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(1000L)+100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    st = bufferDeEntrada.readUTF();

                    String[] array = st.split(":");
                    if(array[0].equals("Conectado")){
                        Conexion conexion = new Conexion();
                        if(array[2].equals(conexion.getUsername())){
                            mensajes.setUsername(conexion.getUsername());
                            mensajes.setMensaje(array[1]);
                            listaMensaje.add(mensajes);
                            System.out.println(listaMensaje);
                            areaChat.setText(listaMensaje.get(0).getMensaje());
                            mensajesEntrada.clear();
                            for(int i=4;i< Arrays.stream(array).count();i++){
                                if (conexion.getUsername().equals(array[i])){

                                }else{
                                    mensajesEntrada.add(array[i]);
                                    chats.setItems(mensajesEntrada);

                                }
                            }
                        }else{
                            String ms = listaMensaje.get(0).getMensaje();
                            mensajes.setUsername(conexion.getUsername());
                            mensajes.setMensaje(ms+"\n"+"Bienvenido"+array[2]);
                            listaMensaje.add(mensajes);
                            areaChat.setText(listaMensaje.get(0).getMensaje());
                            mensajesEntrada.clear();
                            for(int i=4;i< Arrays.stream(array).count();i++){
                                if (conexion.getUsername().equals(array[i])){

                                }else{
                                    mensajesEntrada.add(array[i]);
                                    chats.setItems(mensajesEntrada);

                                }
                            }
                        }
                    }else{
                        for (int i=0; i < listaMensaje.size(); i++) {
                            if (listaMensaje.get(i).getUsername().equals(array[0])) {
                                String sms = listaMensaje.get(i).getMensaje()+"\n"+array[0]+": "+array[2];
                                mensajes.setUsername(array[0]);
                                mensajes.setMensaje(sms);
                                areaChat.setText(listaMensaje.get(i).getMensaje());
                                 bandera = true;
                                i = listaMensaje.size();
                            }else {
                            if (i == listaMensaje.size()-1 || bandera ){
                                bandera = false;
                            }
                        }

                            if (bandera == false){
                                mensajes.setMensaje(array[0]);
                                mensajes.setMensaje(array[0]+": "+array[2]);
                                listaMensaje.add(mensajes);     
                                areaChat.setText(listaMensaje.get(i).getMensaje());
                                bandera = true;
                                i = listaMensaje.size();
                            }
                        }

                    }

                    this.setChanged();
                    this.notifyObservers(st);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }while (!st.equals("FIN"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
