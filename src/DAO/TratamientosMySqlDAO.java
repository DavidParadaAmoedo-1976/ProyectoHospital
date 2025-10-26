package DAO;

import Conexiones.ConexionMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}

