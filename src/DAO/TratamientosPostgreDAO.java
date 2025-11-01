package DAO;

import Conexiones.ConexionMySQL;
import Conexiones.ConexionPostgreSQL;
import Modelo.TratamientosPostgre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TratamientosPostgreDAO {

    public int crear(TratamientosPostgre tratamiento) {
        String sql = "insert into hospital.tratamientos (id_medico, id_especialidad) values (?, ?) returning id_tratamiento";

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
        String sql = "delete from hospital.tratamientos where id_tratamiento = ?";
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

    public static void obtenerTratamientoPorEspecialidad(int id_especialidad) {
        String sql = "select id_tratamiento from hospital.tratamientos where id_especialidad = ?;";
        String sql2 = """
                select p.nombre As "Nombre del Paciente", t.nombre_tratamiento as Tratamiento
                from pacientes p
                join pacientes_tratamientos pt
                on p.id_paciente = pt.id_paciente
                join tratamientos  t
                on t.id_tratamiento = pt.id_Tratamiento
                where t.id_tratamiento = ?
                """;

        try (Connection connPostgre = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement psPostgre = connPostgre.prepareStatement(sql)) {

            psPostgre.setInt(1, id_especialidad);
            try (ResultSet rsPostgre = psPostgre.executeQuery()){

                try (Connection connMySQL = ConexionMySQL.getInstancia().getConexion();
                    PreparedStatement psMySQL = connMySQL.prepareStatement(sql2)) {

                    System.out.print("\nPacientes que recibieron tratamientos de la especialidad: " + id_especialidad);
                    while (rsPostgre.next()) {
                        int id_tratamiento = rsPostgre.getInt("id_tratamiento");
                        System.out.println("\nEl id de tratamiento es: " + id_tratamiento);

                        psMySQL.setInt(1, id_tratamiento);
                        try (ResultSet rsMySQL = psMySQL.executeQuery()) {
                            boolean hayResultados = false;

                            while (rsMySQL.next()) {
                                hayResultados = true;

                                String nombrePaciente = rsMySQL.getString("Nombre del paciente");
                                String tratamiento = rsMySQL.getString("Tratamiento");
                                System.out.println(nombrePaciente + ", " + tratamiento);
                            }
                            if (!hayResultados) {
                                System.out.println("Ese tratamiento no tiene pacientes.");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID del Paciente: " + e.getMessage());
        }
    }
}




