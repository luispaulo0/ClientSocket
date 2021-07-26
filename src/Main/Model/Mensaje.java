package Main.Model;

public class Mensaje {

    private String username;
    private String mensaje;


    public Mensaje(String username, String mensaje) {
        this.username = username;
        this.mensaje = mensaje;
    }
    public Mensaje(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
