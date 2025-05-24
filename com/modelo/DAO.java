package com.modelo;

import java.util.List;

public interface DAO<T> { 
    void crear(T entidad);
    T leerPorId(int id); // Renombrado de leer
    void actualizar(T entidad);
    void eliminarPorId(int id); // Renombrado de eliminar
    List<T> listarTodos();
}
