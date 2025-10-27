package DAO;

import Conexiones.ConexionMySQL;
import Modelo.TratamientosMySql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TratamientosMySqlDAO {

    public void crear(int idTratamiento, String nombre, String descripcion) {
        String sql = "INSERT INTO tratamientos (id_tratamiento, nombre_tratamiento, descripcion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTratamiento);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.executeUpdate();

            System.out.println("Tratamiento insertado en MySQL con ID " + idTratamiento);

        } catch (SQLException e) {
            System.err.println("Error al insertar tratamiento en MySQL: " + e.getMessage());
        }
    }

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
}

