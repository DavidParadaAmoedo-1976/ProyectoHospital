package DAO;

import java.util.List;

public interface CRUD<T> {
    void crear(T entidad);
    List<T> leerTodos();
    void eliminar(int id);
}
