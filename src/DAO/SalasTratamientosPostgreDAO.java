package DAO;

import Conexiones.ConexionMySQL;
import Conexiones.ConexionPostgreSQL;
import Modelo.CitasMySql;
import Modelo.SalasTratamientosPostgre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalasTratamientosPostgreDAO {

    public static void listarTratamientosPorSala() {
        String sql = """
                SELECT s.nombre_sala AS "Nombre de la sala", COUNT(st.id_tratamiento) AS "Cantidad de tratamientos"
                FROM hospital.salas s
                LEFT JOIN hospital.salas_tratamientos st
                ON s.id_sala = st.id_sala
                GROUP BY "Nombre de la sala"
                ORDER BY "Cantidad de tratamientos" DESC
                """;

        try (Connection conn = ConexionPostgreSQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n*** Total de Tratamientos por sala ***");
            while (rs.next()) {
                System.out.printf("\n" + rs.getString("Nombre de la sala") + "\t" + rs.getInt("Cantidad de tratamientos"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener total de citas: " + e.getMessage());
        }
    }
}


