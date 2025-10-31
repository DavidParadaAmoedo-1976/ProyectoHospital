package DAO;
import Conexiones.ConexionPostgreSQL;
import Modelo.MedicosPostgre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicosPostgreDAO{

    public void crear(MedicosPostgre medico) {
        String sql = "INSERT INTO hospital.medicos (nombre_medico, contacto)" +
                     "VALUES (?, ROW(?, ?, ?, ?)::hospital.contacto_medico)";

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

    public List<MedicosPostgre> leerTodos() {
        List<MedicosPostgre> lista = new ArrayList<>();
        String sql = """
                        SELECT id_medico,
                               nombre_medico,
                               (contacto).nombre_contacto AS nombre_contacto,
                               (contacto).nif AS nif,
                               (contacto).telefono AS telefono,
                               (contacto).email AS email
                        FROM hospital.medicos
                        ORDER BY id_medico
                     """;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new MedicosPostgre(
                        rs.getInt("id_medico"),
                        rs.getString("nombre_medico"),
                        rs.getString("nombre_contacto"),
                        rs.getString("nif"),
                        rs.getString("telefono"),
                        rs.getString("email")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar médicos: " + e.getMessage());
        }

        return lista;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM hospital.medicos WHERE id_medico = ?";

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
        String sql = " Select id_medico From hospital.medicos Where (contacto).nif = ?";

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