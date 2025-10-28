package DAO;

import Conexiones.ConexionMySQL;
import Modelo.PacientesTratamientosMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PacientesTratamientosMySqlDAO implements CRUD<PacientesTratamientosMySql> {
    @Override
    public void crear(PacientesTratamientosMySql entidad) {

    }

    @Override
    public List<PacientesTratamientosMySql> leerTodos() {
        return List.of();
    }

    @Override
    public void eliminar(int id) {
    }

    public void tratamientoPorNumeroPacientes(int numero) {
        String sql = """
                Select patr.id_tratamiento, count(patr.id_paciente) as numero_pacientes, min(patr.fecha_inicio) from pacientes_tratamientos patr
                Group by patr.id_tratamiento
                Having Count(patr.id_paciente) <= ?;
                """;
        try (Connection conn = ConexionMySQL.getInstancia().getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numero);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n*** Tratamientos con menos de " + numero + " pacientes ***\n");
            while (rs.next()) {
                int idTratamiento = rs.getInt("id_tratamiento");
                int numPacientes = rs.getInt("numero_pacientes");
                Date fechaInicio = rs.getDate("fecha_inicio");

                System.out.printf("ID Tratamiento: %d | Fecha inicio: %s | Nº Pacientes: %d%n",
                        idTratamiento, fechaInicio, numPacientes);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar los tratamientos " + e.getMessage());
        }
    }
}

