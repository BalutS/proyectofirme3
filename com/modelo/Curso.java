package com.modelo;

import java.util.ArrayList;

// Representa un curso en el sistema educativo.
public class Curso {
    private int codigo; // Campo nuevo para identificar el curso
    private int grado;
    private int grupo;
    private ArrayList<Estudiante> estudiantes;
    private Profesor profesor;

    // Constructor por defecto
    public Curso() {
        this.codigo = 0; // Inicializa el código
        this.estudiantes = new ArrayList<>(); // Inicializa la lista de estudiantes
        this.profesor = new Profesor(); // Inicializa el profesor o se deja nulo según la lógica de negocio
    }

    // Constructor modificado para incluir código
    public Curso(int codigo, int grado, int grupo, ArrayList<Estudiante> estudiantes, Profesor profesor) {
        this.codigo = codigo;
        this.grado = grado;
        this.grupo = grupo;
        this.estudiantes = (estudiantes != null) ? estudiantes : new ArrayList<>();
        this.profesor = (profesor != null) ? profesor : new Profesor(); // O manejar profesor nulo de forma diferente
    }

    // Constructor original - mantenido por compatibilidad o eliminar si no es necesario
    // Por ahora, se asume que podría usarse en otro lugar y se actualiza para llamar al nuevo o inicializar código
    public Curso(int grado, int grupo, ArrayList<Estudiante> estudiantes) {
        this(0, grado, grupo, estudiantes, new Profesor()); // Llama al nuevo constructor, código por defecto a 0
        // Alternativamente, si este constructor es para crear NUEVOS cursos antes de la asignación por DAO:
        // this.codigo = 0; // O algún otro valor por defecto indicando "aún no guardado"
        // this.grado = grado;
        // this.grupo = grupo;
        // this.estudiantes = estudiantes;
        // this.profesor = new Profesor();
    }

    // Getter y Setter para codigo
    public int obtenerCodigo() { // Renombrado de getCodigo
        return codigo;
    }

    public void establecerCodigo(int codigo) { // Renombrado de setCodigo
        this.codigo = codigo;
    }
    
    // Busca un estudiante por su código dentro de la lista de estudiantes del curso.
    public Estudiante buscarEstudiante(int codigo) { // Parámetro 'codigo' ya está bien
        Estudiante estudianteEncontrado = null; // 'est' a 'estudianteEncontrado'
        if (this.estudiantes != null) {
            for (Estudiante estudiante : this.estudiantes) {
                // Se asume que Estudiante.obtenerCodigo() es el método traducido
                if (estudiante.obtenerCodigo() == codigo) { 
                    estudianteEncontrado = estudiante;
                    break; // Estudiante encontrado, no es necesario seguir buscando
                }
            }
        }
        return estudianteEncontrado;
    }
    
    // Devuelve una cadena con la lista de estudiantes del curso.
    public String listarEstudiantes() { // Ya en español
        String listaStr = ""; // 'lis' a 'listaStr'
        if (this.estudiantes != null) {
            for (Estudiante estudiante : this.estudiantes) {
                listaStr += estudiante.toString() + "\n"; // Estudiante.toString() se mantendrá
            }
        }
        return listaStr;
    }
    
    // Devuelve información detallada del curso, incluyendo profesor y lista de estudiantes.
    public String infoCurso() { // Ya en español
        return toString() + "\n"
                + (this.profesor != null ? this.profesor.toString() : "Profesor: N/A") + "\n" // Manejo de profesor nulo
                + "Estudiantes:\n" + listarEstudiantes();
    }
    

    @Override
    public String toString() {
        // Se asume que Profesor.obtenerNombre() es el método traducido
        return "codigo: " + this.codigo + ", curso: " + this.grado + " - " + this.grupo
                + "\n profesor: " + (this.profesor != null ? this.profesor.obtenerNombre() : "N/A"); // toString mejorado
    }

    /**
     * @return el grado
     */
    public int obtenerGrado() { // Renombrado de getGrado
        return grado;
    }

    /**
     * @param grado el grado a establecer
     */
    public void establecerGrado(int grado) { // Renombrado de setGrado
        this.grado = grado;
    }

    /**
     * @return el grupo
     */
    public int obtenerGrupo() { // Renombrado de getGrupo
        return grupo;
    }

    /**
     * @param grupo el grupo a establecer
     */
    public void establecerGrupo(int grupo) { // Renombrado de setGrupo
        this.grupo = grupo;
    }

    /**
     * @return los estudiantes
     */
    public ArrayList<Estudiante> obtenerEstudiantes() { // Renombrado de getEstudiantes
        return estudiantes;
    }

    /**
     * @param estudiantes los estudiantes a establecer
     */
    public void establecerEstudiantes(ArrayList<Estudiante> estudiantes) { // Renombrado de setEstudiantes
        this.estudiantes = estudiantes;
    }

    /**
     * @return el profesor
     */
    public Profesor obtenerProfesor() { // Renombrado de getProfesor
        return profesor;
    }

    /**
     * @param profesor el profesor a establecer
     */
    public void establecerProfesor(Profesor profesor) { // Renombrado de setProfesor
        this.profesor = profesor;
    }
    
    
}

