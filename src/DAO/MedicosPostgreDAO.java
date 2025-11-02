package DAO;

import Conexiones.ConexionPostgreSQL;
import Modelo.MedicosPostgre;
import java.sql.*;

public class MedicosPostgreDAO{

    public static void crear(MedicosPostgre medico) {
        String sql = "insert into hospital.medicos (nombre_medico, contacto)" +
                     "values (?, row(?, ?, ?, ?)::hospital.contacto_medico)";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getNombreContacto());   // nombre_contacto
            ps.setString(3, medico.getNif());      // nif
            ps.setString(4, medico.getTelefono()); // telefono
            ps.setString(5, medico.getEmail());    // email

            ps.executeUpdate();
            System.out.println("Médico insertado correctamente en PostgreSQL.");

        } catch (SQLException e) {
            System.err.println("Error al insertar médico: " + e.getMessage());
        }
    }

    public static void leerTodosReducido() {
        String sql = """
                    select id_medico, nombre_medico
                    from hospital.medicos
                    order by id_medico
                    """;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n  *** Mostrando los médicos disponibles ***");
            while (rs.next()) {
                System.out.println("Id.- " + rs.getInt("id_medico") + " \t->\t" +
                        rs.getString("nombre_medico"));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar médicos: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "delete from hospital.medicos where id_medico = ?";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("Médico eliminado correctamente.");
            else
                System.out.println("No se encontró ningún médico con ese ID.");

        } catch (SQLException e) {
            System.err.println("Error al eliminar el médico: " + e.getMessage());
        }
    }

    public int obtenerIdPorNif(String nif) {
        String sql = "select id_medico from hospital.medicos where (contacto).nif = ?";

        int id = -1;
        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nif);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("el Id del Médico es: " + id);
                return rs.getInt("id_medico");

            } else {
                System.out.println("No se encontro ningun Médico con ese NIF.");
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar el Médico.");
        }
        return -1;
    }
}