package DAO;

import Conexiones.ConexionMySQL;
import Modelo.PacientesMySql;
import java.sql.*;

public class PacientesMySqlDAO {

    public void crear(PacientesMySql paciente) {
        String sql = "insert into pacientes (nombre, email, fecha_nacimiento) values (?, ?, ?)";
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paciente.getNombre());
            ps.setString(2, paciente.getEmail());
            ps.setDate(3, Date.valueOf(paciente.getFechaNacimiento()));
            ps.executeUpdate();
            System.out.println("Se ha creado el paciente correctamente");
        } catch (SQLException e) {
            System.err.println("Error al crear el paciente: " + e.getMessage());
        }
    }

    public static void leerTodosReducido() {
        String sql = "select id_paciente, nombre from pacientes";
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n  *** Lista de pacientes ***");
            while (rs.next()) {
                System.out.println("Id.- " + rs.getInt("id_paciente") + "\tNombre: " + rs.getString("nombre"));
            }

        } catch (SQLException e) {
            System.err.println("Error al leer los pacientes: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "delete from pacientes where id_paciente = ?";
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0)
                System.out.println("Se ha eliminado el paciente");
            else
                System.out.println("No se encontró ningún paciente con ese ID");
        } catch (SQLException e) {
            System.err.println("Error al eliminar el paciente: " + e.getMessage());
        }
    }
}

