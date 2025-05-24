package com.modelo;

public class Profesor extends Persona {
    private Curso curso;

    public Profesor() {
    }

    public Profesor(Curso curso, String nombre, int edad, int cedula, int codigo, String tipo) {
        super(nombre, edad, cedula, codigo, tipo);
        this.curso = curso;
    }
    
    // Califica a un estudiante en una asignatura específica.
    // Se asume que Curso.buscarEstudiante, Estudiante.buscarAsignatura, Asignatura.agregarCalificacion serán los nombres finales o se adaptarán.
    public void calificarEstudiante(int codigoEstudiante, String nombreAsignatura, Calificacion calificacion) { // Parámetros renombrados
        obtenerCurso().buscarEstudiante(codigoEstudiante).buscarAsignatura(nombreAsignatura).agregarCalificacion(calificacion);
    }
    
    // Lista los estudiantes del curso asignado al profesor.
    // Se asume que Curso.listarEstudiantes es el nombre final.
    public String listarEstudiantes() { // Ya en español
        return obtenerCurso().listarEstudiantes();
    }

    @Override
    public String toString() {
        // super.toString() ya está adaptado.
        // obtenerCurso().toString() se manejará cuando se traduzca Curso.toString().
        return super.toString() + (this.curso != null ? obtenerCurso().toString() : ", Sin curso asignado");
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

