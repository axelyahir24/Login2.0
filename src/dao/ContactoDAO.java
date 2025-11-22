package dao;

import conexion.CConexion;
import java.sql.*;
import java.util.ArrayList;
import modelo.Contacto;

public class ContactoDAO {

    public ArrayList<Contacto> listar() {
        ArrayList<Contacto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Contactos";
        try (Connection con = CConexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                lista.add(new Contacto(
                        rs.getInt("idContacto"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar contactos: " + e.getMessage());
        }
        return lista;
    }

    public boolean agregar(Contacto c) {
        String sql = "INSERT INTO Contactos(nombre, telefono, correo) VALUES(?,?,?)";
        try (Connection con = CConexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, c.getNombre());
            pst.setString(2, c.getTelefono());
            pst.setString(3, c.getCorreo());
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al agregar: " + e.getMessage());
            return false;
        }
    }

    public boolean editar(Contacto c) {
        String sql = "UPDATE Contactos SET nombre=?, telefono=?, correo=? WHERE idContacto=?";
        try (Connection con = CConexion.conectar(); 
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, c.getNombre());
            pst.setString(2, c.getTelefono());
            pst.setString(3, c.getCorreo());
            pst.setInt(4, c.getIdContacto());
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al editar: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Contactos WHERE idContacto=?";
        try (Connection con = CConexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            pst.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
            return false;
        }
    }
}
