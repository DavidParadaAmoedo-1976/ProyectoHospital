package DAO;

import Conexiones.ConexionMySQL;
import java.sql.*;

public class CitasMySqlDAO {

    public static void totalCitasPorPaciente() {
        String sql = """
                    select paciente.nombre as nombre_paciente, count(cont.id_cita) as total_citas
                    from pacientes paciente
                    left join citas cont on paciente.id_paciente = cont.id_paciente
                    group by paciente.id_paciente, paciente.nombre
                    order by total_citas desc;
                    """;

        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n*** Total de citas por paciente ***");
            System.out.print("\nNombre del paciente \tNº de citas");
            while (rs.next()) {
                System.out.printf("\n%-20s -> \t%-5d", rs.getString("nombre_paciente") , rs.getInt("total_citas"));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener total de citas: " + e.getMessage());
        }
    }
}
