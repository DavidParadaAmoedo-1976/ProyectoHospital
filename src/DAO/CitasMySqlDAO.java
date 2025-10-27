package DAO;

import Modelo.CitasMySql;

import java.util.List;

public class CitasMySqlDAO implements CRUD<CitasMySql>{
    @Override
    public void crear(CitasMySql entidad) {

    }

    @Override
    public List<CitasMySql> leerTodos() {
        return List.of();
    }

    @Override
    public void eliminar(int id) {

    }
}
