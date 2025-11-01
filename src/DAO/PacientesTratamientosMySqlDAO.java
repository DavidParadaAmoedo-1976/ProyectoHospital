package DAO;

import Conexiones.ConexionMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PacientesTratamientosMySqlDAO {

    public void tratamientoPorNumeroPacientes(int numero) {
        String sql = """
                        select t.id_tratamiento, t.nombre_tratamiento as nombreTratamiento, count(pt.id_paciente) as numeroPacientes
                        from tratamientos t
                        left join pacientes_tratamientos pt
                        on t.id_tratamiento = pt.id_tratamiento
                        group by t.id_tratamiento
                        having count(pt.id_paciente) <= ?
                """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n*** Tratamientos con " + numero + " o menos pacientes ***\n");
            while (rs.next()) {
                String nombreTratamiento = rs.getString("nombreTratamiento");
                int numPacientes = rs.getInt("numeroPacientes");

                System.out.printf("\n%-30s %s" , nombreTratamiento  , "\t-> Nº Pacientes: " + numPacientes);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los tratamientos " + e.getMessage());
        }
    }
}

