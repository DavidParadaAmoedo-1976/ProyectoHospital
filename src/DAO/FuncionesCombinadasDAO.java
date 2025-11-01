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
            Select t.id_tratamiento, e.nombre_especialidad, m.nombre_medico
            From hospital.tratamientos t
            Join hospital.especialidades e ON t.id_especialidad = e.id_especialidad
            Join hospital.medicos m ON t.id_medico = m.id_medico
            Order By t.id_tratamiento;
            """;

        String sqlMySQL = """
            Select id_tratamiento, nombre_tratamiento, descripcion
            From tratamientos
            Order By id_tratamiento;
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

                    System.out.printf("\n          ID: " + idTratamientoMysql +
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
