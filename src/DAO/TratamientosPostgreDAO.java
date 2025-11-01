package DAO;

import Conexiones.ConexionPostgreSQL;
import Modelo.TratamientosPostgre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TratamientosPostgreDAO {

    public int crear(TratamientosPostgre tratamiento) {
        String sql = "INSERT INTO hospital.tratamientos (id_medico, id_especialidad) VALUES (?, ?) RETURNING id_tratamiento";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tratamiento.getIdMedico());
            ps.setInt(2, tratamiento.getIdEspecialidad());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idTratamiento = rs.getInt("id_tratamiento");
                System.out.println("Tratamiento insertado en PostgreSQL con ID " + idTratamiento);
                return idTratamiento;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar tratamiento en PostgreSQL: " + e.getMessage());
        }
        return -1;
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM hospital.tratamientos WHERE id_tratamiento = ?";
        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0)
                System.out.println("Tratamiento eliminado en PostgreSQL.");
            else
                System.out.println("No se encontró ningun tratamiento con ese ID en PostgreSQL.");

        } catch (SQLException e) {
            System.err.println("Error al eliminar en PostgreSQL: " + e.getMessage());
        }
    }

    public int obtenerIdTratamientoPorEspecialidad(){
        String sql = "Select id_tratamiento From hospital.tratamientos Where id_especialidad = ?;";
        int id_especialidad = -1;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps =  conn.prepareStatement(sql)){

            ps.executeQuery();

            ps.setInt(1, id_especialidad);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_tratamiento");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID del tratamiento por especialidad: " + e.getMessage());
        }
        return -1;
    }
}