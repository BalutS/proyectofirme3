/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.modelo;

import java.util.ArrayList;

/**
 *
 * @author river
 */
public class Asignatura {
    private int codigo; // New field
    private String nombre;
    private ArrayList<Calificacion> calificaciones;

    public Asignatura() {
        this.codigo = 0; // Initialize codigo
        this.calificaciones = new ArrayList<>();
    }

    public Asignatura(String nombre) {
        this(); // Calls default constructor to initialize codigo and calificaciones list
        this.nombre = nombre;
    }

    // Constructor for full reconstruction including codigo
    public Asignatura(int codigo, String nombre, ArrayList<Calificacion> calificaciones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.calificaciones = (calificaciones != null) ? calificaciones : new ArrayList<>();
    }

    // Getter and Setter for codigo
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public void agregarCalificacion(Calificacion cal){
        getCalificaciones().add(cal);
    }
    
    public String listarCalificaiones () {
        String lis = "";
        for (Calificacion calificacion : getCalificaciones()) {
            lis += calificacion.toString() + "\n";
        }
        return lis;
    }
    
    public float promedio(){
        if (calificaciones == null || calificaciones.isEmpty()) {
            return 0.0f; // Or handle as an error/NaN as appropriate
        }
        float sum = 0;
        for (Calificacion calificacion : calificaciones) {
            sum += calificacion.getNota();
        }
        return sum / calificaciones.size();
    }

    @Override
    public String toString() {
        // Consider if promedio() can cause issues if calificaciones is empty or null
        float avg = 0.0f;
        if (calificaciones != null && !calificaciones.isEmpty()) {
            avg = promedio();
        }
        return "Asignatura{" + "codigo=" + codigo + ", nombre=" + nombre + ", promedio_calificaciones=" + avg + '}';
    }
    
    

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the calificaciones
     */
    public ArrayList<Calificacion> getCalificaciones() {
        return calificaciones;
    }

    /**
     * @param calificaciones the calificaciones to set
     */
    public void setCalificaciones(ArrayList<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }
}
