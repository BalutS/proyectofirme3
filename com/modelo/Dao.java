package com.modelo;

import java.util.List;

public interface Dao<T> {
    void crear(T entidad);
    T leer(int id);
    void actualizar(T entidad);
    void eliminar(int id);
    List<T> listarTodos();
}
