package Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgreSQL {
    private static ConexionPostgreSQL instancia;
    private static Connection conexion;
    private final String URL = "jdbc:postgresql://localhost:5432/hospital_postgre";
    private final String USUARIO = "postgres";
    private final String PASSWORD = "Diego-230911";

    private ConexionPostgreSQL() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conectado a PostgreSQL correctamente.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConexionPostgreSQL getInstancia() throws SQLException {
        if (instancia == null || conexion.isClosed()) {
            instancia = new ConexionPostgreSQL();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión Postgre cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión Postgre: " + e.getMessage());
        }
    }
}
