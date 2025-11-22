package controlador;

import conexion.CConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CLogin {

    public boolean entrarSiValido(String usuario, String contrasenia) {
        String sql = "SELECT * FROM Usuarios WHERE ingresoUsuario=? AND ingresoContrasenia=?";

        try (Connection con = CConexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, contrasenia);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // Si encuentra un registro, acceso valido

        } catch (Exception e) {
            System.out.println("Error en login: " + e);
            return false;
        }
    }
}






