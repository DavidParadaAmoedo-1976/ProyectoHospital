package DAO;

import Conexiones.ConexionPostgreSQL;
import java.sql.*;

public class TratamientosPostgreDAO {

    public int crearTratamiento(int idEspecialidad, int idMedico) {
        int idTratamiento = -1;
        String sql = """
            INSERT INTO hospital.tratamientos (id_especialidad, id_medico)
            VALUES (?, ?)
            RETURNING id_tratamiento;
        """;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEspecialidad);
            ps.setInt(2, idMedico);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTratamiento = rs.getInt("id_tratamiento");
                System.out.println("Tratamiento creado en PostgreSQL con ID " + idTratamiento);
            }

        } catch (SQLException e) {
            System.err.println("Error al insertar tratamiento en PostgreSQL: " + e.getMessage());
        }

        return idTratamiento;
    }
}
