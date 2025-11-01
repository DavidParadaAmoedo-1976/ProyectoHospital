package DAO;

import Conexiones.ConexionMySQL;
import Modelo.TratamientosMySql;
import java.sql.*;

public class TratamientosMySqlDAO {

    public void crear(TratamientosMySql nuevoTratamiento) {
        String sql = "insert into tratamientos (id_tratamiento, nombre_tratamiento, descripcion) values (?, ?, ?)";

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuevoTratamiento.getIdTratamiento());
            ps.setString(2, nuevoTratamiento.getNombreTratamiento());
            ps.setString(3, nuevoTratamiento.getDescripcion());
            ps.executeUpdate();

            System.out.println("Tratamiento insertado en MySQL con ID " + nuevoTratamiento.getIdTratamiento());

        } catch (SQLException e) {
            System.err.println("Error al insertar tratamiento en MySQL: " + e.getMessage());
        }
    }

    public static void leerTodosReducido() {
        String sql = """
                    select id_tratamiento, nombre_tratamiento
                    from tratamientos
                    order by id_tratamiento
                    """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n\t*** Lista de tratamientos ***");
            while (rs.next()) {
                System.out.println("Id.- " +rs.getInt("id_tratamiento") + "\t->\t" + rs.getString("nombre_tratamiento"));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los tratamientos: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        String sql = "delete from tratamientos where id_tratamiento = ?";
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
        String sql = "select id_tratamiento from tratamientos where nombre_tratamiento = ?";
        int id = -1;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreTratamiento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_tratamiento");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID del tratamiento: " + e.getMessage());
        }
        return -1;
    }
}
