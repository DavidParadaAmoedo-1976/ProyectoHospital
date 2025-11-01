package DAO;

import Conexiones.ConexionMySQL;
import Modelo.CitasMySql;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitasMySqlDAO {
/*
    public void crear(CitasMySql cita) {
        String sql = "insert into citas (id_paciente, fecha) values (?, ?)";
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

    public List<CitasMySql> leerTodos() {
        List<CitasMySql> citas = new ArrayList<>();
        String sql = "select id_cita, id_paciente, fecha from citas";

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

    public void eliminar(int id) {
        String sql = "delete from citas where id_cita = ?";
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
*/
    public static void totalCitasPorPaciente() {
        String sql = """
                    select paciente.nombre as nombre_paciente, count(cont.id_cita) as total_citas
                    from pacientes paciente
                    left join citas cont on paciente.id_paciente = cont.id_paciente
                    group by paciente.id_paciente, paciente.nombre
                    order by total_citas desc;
                    """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n*** Total de citas por paciente ***");
            System.out.print("\nNombre del paciente \tNº de citas");
            while (rs.next()) {
                System.out.printf("\n%-20s -> \t%-5d", rs.getString("nombre_paciente") , rs.getInt("total_citas"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener total de citas: " + e.getMessage());
        }
    }
}
