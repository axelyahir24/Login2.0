package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class CConexion {

    public static Connection conectar() {
        try {
            String url = "jdbc:mysql://localhost:3306/agenda_bd?useSSL=false";
            String user = "root";
            String password = "3ird1";  // tu contraseña correcta

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a MySQL!");
            return con;

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}






