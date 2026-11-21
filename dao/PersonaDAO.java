package dao;

import conexion.ConexionMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Persona;

public class PersonaDAO {
    
    public boolean insertar(Persona p) {
        String sql = "INSERT INTO personal (nombre, apellido, telefono, email, direccion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getApellido());
            ps.setString(3, p.getTelefono());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getDireccion());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Persona> listar() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, apellido, telefono, email, direccion FROM personal ORDER BY id DESC";
        
        try (Connection conn = ConexionMySQL.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Persona(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getString("direccion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(Persona p) {
        String sql = "UPDATE personal SET nombre = ?, apellido = ?, telefono = ?, email = ?, direccion = ? WHERE id = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre().trim());
            ps.setString(2, p.getApellido().trim());
            ps.setString(3, p.getTelefono().trim());
            ps.setString(4, p.getEmail().trim());
            ps.setString(5, p.getDireccion().trim());
            ps.setInt(6, p.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM personal WHERE id = ?";
        try (Connection conn = ConexionMySQL.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
