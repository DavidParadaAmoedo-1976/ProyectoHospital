package DAO;

import Conexiones.ConexionMySQL;
import Modelo.PacientesMySql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientesMySqlDAO {

    public void crear(PacientesMySql paciente) {
        String sql = "INSERT INTO pacientes (nombre, email, fecha_nacimiento) VALUES (?, ?, ?)";
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

    public List<PacientesMySql> leerTodos() {
        List<PacientesMySql> lista = new ArrayList<>();
        String sql = "SELECT * FROM pacientes";
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new PacientesMySql(
                        rs.getInt("id_paciente"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDate("fecha_nacimiento").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al leer los pacientes: " + e.getMessage());
        }
        return lista;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM pacientes WHERE id_paciente = ?";
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

