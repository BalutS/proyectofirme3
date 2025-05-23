package com.modelo;

import java.util.ArrayList;

public class Curso {
    private int codigo; // New field
    private int grado;
    private int grupo;
    private ArrayList<Estudiante> estudiantes;
    private Profesor profesor;

    public Curso() {
        this.codigo = 0; // Initialize codigo
        this.estudiantes = new ArrayList<>(); // Initialize list
        this.profesor = new Profesor(); // Initialize professor or leave null as per logic
    }

    // Modified constructor to include codigo
    public Curso(int codigo, int grado, int grupo, ArrayList<Estudiante> estudiantes, Profesor profesor) {
        this.codigo = codigo;
        this.grado = grado;
        this.grupo = grupo;
        this.estudiantes = (estudiantes != null) ? estudiantes : new ArrayList<>();
        this.profesor = (profesor != null) ? profesor : new Profesor(); // Or handle null profesor differently
    }

    // Original constructor - kept for compatibility or remove if not needed
    // For now, let's assume it might be used elsewhere and update it to call the new one or initialize codigo
    public Curso(int grado, int grupo, ArrayList<Estudiante> estudiantes) {
        this(0, grado, grupo, estudiantes, new Profesor()); // Calls the new constructor, default codigo to 0
        // Alternatively, if this constructor is meant for creating NEW courses before DAO assignment:
        // this.codigo = 0; // Or some other default indicating "not yet saved"
        // this.grado = grado;
        // this.grupo = grupo;
        // this.estudiantes = estudiantes;
        // this.profesor = new Profesor();
    }

    // Getter and Setter for codigo
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    
    public Estudiante buscarEstudiante(int codigo){
        Estudiante est = null;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCodigo() == codigo) {
                est = estudiante;
            }
        }
        return est;
    }
    
    public String listarEstudiantes () {
        String lis = "";
        for (Estudiante estudiante : estudiantes) {
            lis += estudiante.toString();
        }
        return lis;
    }
    
    public String infoCurso(){
        return toString() + "\n"
                + profesor.toString()
                + listarEstudiantes();
    }
    

    @Override
    public String toString() {
        return "codigo: " + codigo + ", curso: " + grado + " - " + grupo
                + "\n profesor: " + (profesor != null ? profesor.getNombre() : "N/A"); // Improved toString
    }

    /**
     * @return the grado
     */
    public int getGrado() {
        return grado;
    }

    /**
     * @param grado the grado to set
     */
    public void setGrado(int grado) {
        this.grado = grado;
    }

    /**
     * @return the grupo
     */
    public int getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the estudiantes
     */
    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    /**
     * @param estudiantes the estudiantes to set
     */
    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    /**
     * @return the profesor
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    
    
}

