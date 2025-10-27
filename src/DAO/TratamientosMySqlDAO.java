package DAO;

import Conexiones.ConexionMySQL;
import Modelo.TratamientosMySql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientosMySqlDAO implements CRUD<TratamientosMySql> {
    @Override
    public void crear(TratamientosMySql tratamiento) {
        String sql = "INSERT INTO tratamientos (id_tratamiento, nombre_tratamiento, descripcion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tratamiento.getIdTratamiento());
            ps.setString(2, tratamiento.getNombreTratamiento());
            ps.setString(3, tratamiento.getDescripcion());
            ps.executeUpdate();

            System.out.println("Tratamiento insertado en MySQL con ID " + tratamiento.getIdTratamiento());

        } catch (SQLException e) {
            System.err.println("Error al insertar tratamiento en MySQL: " + e.getMessage());
        }
    }
    @Override
    public List<TratamientosMySql> leerTodos() {
        List<TratamientosMySql> lista = new ArrayList<>();
        String sql = """
                   SELECT id_tratamiento, nombre_tratamiento, descripcion
                   FROM tratamientos
                   ORDER BY id_tratamiento
                """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new TratamientosMySql(
                        rs.getInt("id_tratamiento"),
                        rs.getString("nombre_tratamiento"),
                        rs.getString("descripcion")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los tratamientos: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM tratamientos WHERE id_tratamiento = ?";
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0)
                System.out.println("Se ha eliminado el tratamiento");
            else
                System.out.println("No se encontró ningún tratamiento con ese ID");
        } catch (SQLException e) {
            System.err.println("Error al eliminar el tratamiento: " + e.getMessage());
        }
    }

    public int obtenerIdPorNombre(String nombreTratamiento) {
        String sql = "SELECT id_tratamiento FROM tratamientos WHERE nombre_tratamiento = ?";
        int id = -1;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreTratamiento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id_tratamiento");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener ID del tratamiento por nombre: " + e.getMessage());
        }

        return id;
    }

}




