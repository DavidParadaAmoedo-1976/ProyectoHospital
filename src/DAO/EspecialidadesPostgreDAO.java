package DAO;

import Conexiones.ConexionPostgreSQL;
import Modelo.EspecialidadesPostgre;
import java.sql.*;

public class EspecialidadesPostgreDAO{

    public void crear(EspecialidadesPostgre especialidadesPostgre) {
        String sql = "insert into hospital.especialidades (nombre_especialidad) values (?)";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialidadesPostgre.getNombre_especialidad());

            ps.executeUpdate();
            System.out.println("Especialidad insertada correctamente en PostgreSQL.");

        } catch (SQLException e) {
            System.err.println("Error al insertar especialidad: " + e.getMessage());
        }
    }

    public static void leerTodos() {
        String sql = "select id_especialidad, nombre_especialidad from hospital.especialidades";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n  *** Mostrando las especialidades disponibles ***");
            while (rs.next()) {
                System.out.println("ID.- " + rs.getInt("id_especialidad") + "\t->\t " + rs.getString("nombre_especialidad")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al leer las especialidades: " + e.getMessage());
        }
    }

    public int obtenerIdPorNombre(String nombre) {
        String sql = "select id_especialidad from hospital.especialidades where nombre_especialidad = ?";
        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return rs.getInt("id_especialidad");

        } catch (SQLException e) {
            System.err.println("Error al obtener ID de especialidad: " + e.getMessage());
        }
        return -1;
    }
}