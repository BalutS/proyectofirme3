package com.modelo;

import java.util.ArrayList;

public class Estudiante extends Persona {
    private Curso curso;
    private ArrayList<Asignatura> asignaturas;

    public Estudiante() {
        super(); // Calls Persona's default constructor
        this.asignaturas = new ArrayList<>();
        this.curso = new Curso(); // Or some default initialization
    }

    public Estudiante(ArrayList<Asignatura> asignaturas, String nombre, int edad, int cedula, int codigo, String tipo) {
        super(nombre, edad, cedula, codigo, tipo);
        curso = new Curso();
        this.asignaturas = asignaturas;
    }
    
    // Busca una asignatura por nombre en la lista de asignaturas del estudiante.
    public Asignatura buscarAsignatura(String nombre) { // El parámetro 'nombre' ya está en español
        Asignatura asignaturaEncontrada = null; // 'asig' a 'asignaturaEncontrada'
        // Se asume que Asignatura.obtenerNombre() será el nuevo nombre después de traducir Asignatura.java
        // y que obtenerAsignaturas() es el nuevo nombre para getAsignaturas()
        for (Asignatura asignatura : obtenerAsignaturas()) { 
            if (asignatura.obtenerNombre().equalsIgnoreCase(nombre)) { // Asumiendo Asignatura.obtenerNombre()
                asignaturaEncontrada = asignatura;
            }
        }
        return asignaturaEncontrada;
    }
    
    // Genera un reporte académico del estudiante.
    public String reporteAcademico() { // Ya en español
        return toString() + "\n Asignaturas: \n" +  promedioAsignaturas() + "\n Promedio General:" + promedioGeneral();
    }
    
    // Calcula y formatea el promedio de las asignaturas.
    private String promedioAsignaturas() {
        String listaStr = ""; // 'lis' a 'listaStr'
        if (this.asignaturas != null) {
            for (Asignatura asignatura : this.asignaturas) {
                listaStr += asignatura.toString() + "\n"; // Asignatura.toString() se mantendrá
            }
        }
        return listaStr;
    }
    
    // Calcula el promedio general de todas las asignaturas.
    private float promedioGeneral() {
        if (this.asignaturas == null || this.asignaturas.isEmpty()) {
            return 0.0f; // Manejo de lista vacía o nula
        }
        float suma = 0; // 'sum' a 'suma'
        for (Asignatura asignatura : this.asignaturas) {
            // Asignatura.promedio() ya está en español
            suma += asignatura.promedio(); 
        }
        return suma / this.asignaturas.size();
    }

    @Override
    public String toString() {
        // super.toString() llamará al toString() de Persona (ya modificado)
        // curso.toString() se manejará cuando se traduzca Curso.java
        return super.toString() + ", curso:" + (this.curso != null ? this.curso.toString() : "N/A");
    }
    
    
    // Obtiene la lista de asignaturas del estudiante.
    public ArrayList<Asignatura> obtenerAsignaturas() { // Renombrado de getAsignaturas
        return asignaturas;
    }

    /**
     * @param asignaturas las asignaturas a establecer
     */
    public void establecerAsignaturas(ArrayList<Asignatura> asignaturas) { // Renombrado de setAsignaturas
        this.asignaturas = asignaturas;
    }

    /**
     * @return el curso
     */
    public Curso obtenerCurso() { // Renombrado de getCurso
        return curso;
    }

    /**
     * @param curso el curso a establecer
     */
    public void establecerCurso(Curso curso) { // Renombrado de setCurso
        this.curso = curso;
    }
    
}

