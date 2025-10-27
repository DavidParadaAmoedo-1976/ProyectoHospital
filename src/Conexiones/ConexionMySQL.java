package Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private static ConexionMySQL instancia;
    private static Connection conexion;
    private final String URL = "jdbc:mysql://localhost:3306/hospital_mysql";
    private final String USUARIO = "root";
    private final String PASSWORD = "abc123.";

    private ConexionMySQL() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conectado a MySQL correctamente.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized ConexionMySQL getInstancia() throws SQLException {
        if (instancia == null  || conexion.isClosed()) {
            instancia = new ConexionMySQL();
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
                System.out.println("Conexión MySQL cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión MySQL: " + e.getMessage());
        }
    }
}
