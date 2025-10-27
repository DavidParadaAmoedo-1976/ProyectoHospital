package DAO;

import Modelo.PacientesTratamientosMySql;

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
}
