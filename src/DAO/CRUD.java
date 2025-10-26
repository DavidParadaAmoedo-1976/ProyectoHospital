package DAO;

import java.util.List;

public interface CRUD<T> {
    void crear(T entidad);
    T leerPorId(int id);
    List<T> leerTodos();
    void actualizar(T entidad);
    void eliminar(int id);
}
