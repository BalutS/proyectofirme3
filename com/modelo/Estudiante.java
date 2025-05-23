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
    
    public Asignatura buscarAsignatura(String nombre){
        Asignatura asig = null;
        for (Asignatura asignatura : getAsignaturas()) {
            if (asignatura.getNombre().equalsIgnoreCase(nombre)) {
                asig = asignatura;
            }
        }
        return asig;
    }
    
    public String reporteAcademico(){
        return toString() + "\n Asignaturas: \n" +  promedioAsignaturas() + "\n Promedio General:" + promedioGeneral();
    }
    
    private String promedioAsignaturas(){
        String lis = "";
        for (Asignatura asignatura : asignaturas) {
            lis = asignatura.toString() + "\n";
        }
        return lis;
    }
    
    private float promedioGeneral() {
        if (asignaturas == null || asignaturas.isEmpty()) {
            return 0.0f; // Or handle as an error/NaN as appropriate
        }
        float sum = 0;
        for (Asignatura asignatura : asignaturas) {
            // Ensure asignatura.promedio() also handles potential empty calificaciones
            sum += asignatura.promedio(); 
        }
        return sum / asignaturas.size();
    }

    @Override
    public String toString() {
        return super.toString() + ", curso:" + curso.toString();
    }
    
    

    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    /**
     * @param asignaturas the asignaturas to set
     */
    public void setAsignaturas(ArrayList<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    /**
     * @return the curso
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     * @param curso the curso to set
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }
    
}

