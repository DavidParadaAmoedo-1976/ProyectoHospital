package DAO;
import Conexiones.ConexionPostgreSQL;
import Modelo.MedicosPostgre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicosPostgreDAO implements CRUD<MedicosPostgre> {

    @Override
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

    @Override
    public MedicosPostgre leerPorId(int id) {
        String sql = "SELECT id_medico, nombre_medico, (contacto).nombre_contacto AS nombre_contacto," +
                     "(contacto).nif AS nif, (contacto).telefono AS telefono, (contacto).email AS email" +
                     "FROM hospital.medicos WHERE id_medico = ?";

        MedicosPostgre medico = null;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                medico = new MedicosPostgre(
                        rs.getInt("id_medico"),
                        rs.getString("nombreMedico"),
                        rs.getString("nombreContacto"),
                        rs.getString("nif"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al leer médico: " + e.getMessage());
        }

        return medico;
    }

    @Override
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

    @Override
    public void actualizar(MedicosPostgre medico) {
        String sql = """
            UPDATE hospital.medicos
            SET nombre_medico = ?,
                contacto = ROW(?, ?, ?, ?)::hospital.contacto_medico
            WHERE id_medico = ?
        """;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, medico.getNombre());
            ps.setString(2, medico.getNombre());
            ps.setString(3, medico.getNif());
            ps.setString(4, medico.getTelefono());
            ps.setString(5, medico.getEmail());
            ps.setInt(6, medico.getIdMedico());

            int filas = ps.executeUpdate();
            if (filas > 0)
                System.out.println("Médico actualizado correctamente.");
            else
                System.out.println("No se encontró ningún médico con ese ID.");

        } catch (SQLException e) {
            System.err.println("Error al actualizar el médico: " + e.getMessage());
        }
    }

    @Override
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
}