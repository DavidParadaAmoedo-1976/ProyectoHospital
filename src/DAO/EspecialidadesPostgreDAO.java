package DAO;

import Conexiones.ConexionPostgreSQL;
import Modelo.EspecialidadesPostgre;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class EspecialidadesPostgreDAO{


    public void crear(EspecialidadesPostgre especialidadesPostgre) {
        String sql = "INSERT INTO hospital.especialidades (nombre_especialidad)" +
                "VALUES (?)";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialidadesPostgre.getNombre_especialidad());

            ps.executeUpdate();
            System.out.println("Especialñidad insertada correctamente en PostgreSQL.");

        } catch (SQLException e) {
            System.err.println("Error al insertar especialidad: " + e.getMessage());
        }

    }

    public List<EspecialidadesPostgre> leerTodos() {
        List<EspecialidadesPostgre> lista = new ArrayList<>();
        String sql = "SELECT id_especialidad, nombre_especialidad FROM hospital.especialidades";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                EspecialidadesPostgre especialidadesPostgre = new EspecialidadesPostgre(
                        rs.getInt("id_especialidad"),
                        rs.getString("nombre_especialidad")
                );
                lista.add(especialidadesPostgre);
            }
        } catch (SQLException e) {
            System.err.println("Error al leer las especialidades: " + e.getMessage());
        }
        return lista;
    }

    public int obtenerIdPorNombre(String nombre) {
        String sql = "SELECT id_especialidad FROM hospital.especialidades WHERE nombre_especialidad = ?";
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