/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.modelo;

import java.util.ArrayList;

/**
 * Representa una asignatura en el sistema.
 * @author river // Mantener autor original si se desea
 */
public class Asignatura {
    private int codigo; 
    private String nombre;
    private ArrayList<Calificacion> calificaciones;

    // Constructor por defecto
    public Asignatura() {
        this.codigo = 0; // Inicializa el código
        this.calificaciones = new ArrayList<>();
    }

    public Asignatura(String nombre) {
        this(); // Llama al constructor por defecto para inicializar código y lista de calificaciones
        this.nombre = nombre;
    }

    // Constructor para reconstrucción completa incluyendo código
    public Asignatura(int codigo, String nombre, ArrayList<Calificacion> calificaciones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.calificaciones = (calificaciones != null) ? calificaciones : new ArrayList<>();
    }

    // Getter y Setter para codigo
    public int obtenerCodigo() { // Renombrado de getCodigo
        return codigo;
    }

    public void establecerCodigo(int codigo) { // Renombrado de setCodigo
        this.codigo = codigo;
    }
    
    // Agrega una calificación a la lista de calificaciones de la asignatura.
    public void agregarCalificacion(Calificacion calificacion) { // Parámetro 'cal' a 'calificacion'
        obtenerCalificaciones().add(calificacion); // Usa obtenerCalificaciones
    }
    
    // Lista todas las calificaciones de la asignatura.
    public String listarCalificaciones() { // Corregido 'Calificaiones' a 'Calificaciones'
        String listaStr = ""; // 'lis' a 'listaStr'
        if (this.calificaciones != null) {
            for (Calificacion calificacion : obtenerCalificaciones()) { // Usa obtenerCalificaciones
                listaStr += calificacion.toString() + "\n"; // Calificacion.toString() ya fue adaptado
            }
        }
        return listaStr;
    }
    
    // Calcula el promedio de las calificaciones de la asignatura.
    public float promedio() { // Ya en español
        if (this.calificaciones == null || this.calificaciones.isEmpty()) {
            return 0.0f; // Manejo de lista vacía o nula
        }
        float suma = 0; // 'sum' a 'suma'
        for (Calificacion calificacion : this.calificaciones) {
            suma += calificacion.obtenerNota(); // Usa Calificacion.obtenerNota()
        }
        return suma / this.calificaciones.size();
    }

    @Override
    public String toString() {
        // Considerar si promedio() puede causar problemas si calificaciones es nulo o vacío
        float promedioCalc = 0.0f; // 'avg' a 'promedioCalc'
        if (this.calificaciones != null && !this.calificaciones.isEmpty()) {
            promedioCalc = promedio();
        }
        return "Asignatura{" + "codigo=" + this.codigo + ", nombre=" + this.nombre + ", promedio_calificaciones=" + promedioCalc + '}';
    }
    
    

    /**
     * @return el nombre
     */
    public String obtenerNombre() { // Renombrado de getNombre
        return nombre;
    }

    /**
     * @param nombre el nombre a establecer
     */
    public void establecerNombre(String nombre) { // Renombrado de setNombre
        this.nombre = nombre;
    }

    /**
     * @return las calificaciones
     */
    public ArrayList<Calificacion> obtenerCalificaciones() { // Renombrado de getCalificaciones
        return calificaciones;
    }

    /**
     * @param calificaciones las calificaciones a establecer
     */
    public void establecerCalificaciones(ArrayList<Calificacion> calificaciones) { // Renombrado de setCalificaciones
        this.calificaciones = calificaciones;
    }
}
