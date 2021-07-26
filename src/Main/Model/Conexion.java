package Main.Model;

public class Conexion {

    static private int puerto;
    static private String IP;
    static private String username;

    public Conexion() {
    }
    public Conexion(int puerto, String IP, String username){
        this.puerto = puerto;
        this.IP = IP;
        this.username = username;
    }

    public static int getPuerto() {
        return puerto;
    }

    public static void setPuerto(int puerto) {
        Conexion.puerto = puerto;
    }

    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        Conexion.IP = IP;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Conexion.username = username;
    }




}
