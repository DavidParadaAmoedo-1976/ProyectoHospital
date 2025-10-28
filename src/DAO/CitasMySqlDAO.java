package DAO;

import Conexiones.ConexionMySQL;
import Modelo.CitasMySql;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CitasMySqlDAO implements CRUD<CitasMySql> {

    @Override
    public void crear(CitasMySql cita) {
        String sql = "INSERT INTO citas (id_paciente, fecha) VALUES (?, ?)";
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cita.getIdPaciente());
            ps.setDate(2, Date.valueOf(cita.getFecha()));
            ps.executeUpdate();

            System.out.println("Cita creada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al crear la cita: " + e.getMessage());
        }
    }

    @Override
    public List<CitasMySql> leerTodos() {
        List<CitasMySql> citas = new ArrayList<>();
        String sql = "SELECT id_cita, id_paciente, fecha FROM citas";

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                CitasMySql cita = new CitasMySql(
                        rs.getInt("id_cita"),
                        rs.getInt("id_paciente"),
                        rs.getDate("fecha").toLocalDate()
                );
                citas.add(cita);
            }

        } catch (SQLException e) {
            System.err.println("Error al leer las citas: " + e.getMessage());
        }

        return citas;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM citas WHERE id_cita = ?";
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("Cita eliminada correctamente.");
            else
                System.out.println("No se encontró ninguna cita con ese ID.");

        } catch (SQLException e) {
            System.err.println("Error al eliminar la cita: " + e.getMessage());
        }
    }

    public void totalCitasPorPaciente() {
        String sql = """
                SELECT paciente.nombre as nombre_paciente, COUNT(cont.id_cita) AS total_citas
                                FROM pacientes paciente
                                LEFT JOIN citas cont ON paciente.id_paciente = cont.id_paciente
                                GROUP BY paciente.id_paciente, paciente.nombre
                                ORDER BY total_citas DESC;
            """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n*** Total de citas por paciente ***");
            while (rs.next()) {
                System.out.printf("\n" + rs.getString("nombre_paciente") + "\t" + rs.getInt("total_citas"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener total de citas: " + e.getMessage());
        }
    }

}
