package DAO;

import Conexiones.ConexionMySQL;
import Conexiones.ConexionPostgreSQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FuncionesCombinadasDAO {

    public static void listarTratamientosConEspecialidadesYMedicos() {

        System.out.println("\n*** Listado completo de tratamientos ***\n");

        String sqlPostgre = """
                            select t.id_tratamiento, e.nombre_especialidad, m.nombre_medico
                            from hospital.tratamientos t
                            join hospital.especialidades e
                            on t.id_especialidad = e.id_especialidad
                            join hospital.medicos m ON t.id_medico = m.id_medico
                            order by t.id_tratamiento;
                            """;

        String sqlMySQL = """
                            select id_tratamiento, nombre_tratamiento, descripcion
                            from tratamientos
                            order by id_tratamiento;
                            """;

        try (
                Connection connPostgre = ConexionPostgreSQL.getInstancia().getConexion();
                Statement stPostgre = connPostgre.createStatement();
                ResultSet rsPostgre = stPostgre.executeQuery(sqlPostgre);

                Connection connMySQL = ConexionMySQL.getInstancia().getConexion();
                Statement stMySQL = connMySQL.createStatement();
                ResultSet rsMySQL = stMySQL.executeQuery(sqlMySQL)
        ) {

            while (rsPostgre.next() && rsMySQL.next()) {
                int idTratamientoPostgre = rsPostgre.getInt("id_tratamiento");
                int idTratamientoMysql = rsMySQL.getInt(("id_tratamiento"));

                if (idTratamientoMysql == idTratamientoPostgre) {
                    String nombre = rsMySQL.getString("nombre_tratamiento");
                    String descripcion = rsMySQL.getString("descripcion");
                    String especialidad = rsPostgre.getString("nombre_especialidad");
                    String medico = rsPostgre.getString("nombre_medico");

                    System.out.print("\n          ID: " + idTratamientoMysql +
                            "\n Tratamiento: " + nombre +
                            "\n Descripción: " + descripcion +
                            "\nEspecialidad: " + especialidad +
                            "\n      Médico: " + medico +
                            "\n-------------------------------------------");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar los tratamientos: " + e.getMessage());
        }
    }
}
