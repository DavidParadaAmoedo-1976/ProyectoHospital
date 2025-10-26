package DAO;

import Conexiones.ConexionPostgreSQL;
import Modelo.EspecialidadesPostgre;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class EspecialidadesPostgreDAO implements CRUD<EspecialidadesPostgre> {


    @Override
    public void crear(EspecialidadesPostgre especialidadesPostgre) {
        String sql = "INSERT INTO hospital.especialidades (nombre_especialidad)" +
                "VALUES (?)";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especialidadesPostgre.getNombre_especialidad());

            ps.executeUpdate();
            System.out.println("Médico insertado correctamente en PostgreSQL.");

        } catch (SQLException e) {
            System.err.println("Error al insertar médico: " + e.getMessage());
        }

    }

    @Override
    public EspecialidadesPostgre leerPorId(int id) {
        return null;
    }


    @Override
    public void actualizar(EspecialidadesPostgre entidad) {

    }

    @Override
    public void eliminar(int id) {

    }

    @Override
    public List<EspecialidadesPostgre> leerTodos() {
        List<EspecialidadesPostgre> lista = new ArrayList<>();
        String sql = "SELECT id_especialidad, nombre_especialidad FROM hospital.especialidades";

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                EspecialidadesPostgre e = new EspecialidadesPostgre(
                        rs.getInt("id_especialidad"),
                        rs.getString("nombre_especialidad")
                );
                lista.add(e);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al leer especialidades: " + e.getMessage());
        }

        return lista;
    }


}