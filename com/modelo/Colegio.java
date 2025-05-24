package com.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Define la clase Colegio que gestiona la lógica de negocio principal.
public class Colegio {
    private String nombre;
    // Campos DAO ya actualizados a la convención XXXDAO
    private final EstudianteDAO estudianteDAO;
    private final ProfesorDAO profesorDAO;
    private final CursoDAO cursoDAO;
    private final AsignaturaDAO asignaturaDAO;

    public Colegio(String nombre) {
        this.nombre = nombre;
        this.estudianteDAO = new EstudianteDAO();
        this.profesorDAO = new ProfesorDAO();
        this.cursoDAO = new CursoDAO();
        this.asignaturaDAO = new AsignaturaDAO();
    }

    // --- Métodos para agregar entidades ---
    public void agregarEstudiante(Estudiante estudiante) { // Parámetro 'est' a 'estudiante'
        if (estudiante == null) return;
        estudianteDAO.crear(estudiante);
    }

    public void agregarProfesor(Profesor profesor) { // Parámetro 'prof' a 'profesor'
        if (profesor == null) return;
        profesorDAO.crear(profesor);
    }

    public void agregarCurso(Curso curso) {
        if (curso == null) return;
        cursoDAO.crear(curso);
    }

    public void agregarAsignatura(Asignatura asignatura) { // Parámetro 'asig' a 'asignatura'
        if (asignatura == null) return;
        asignaturaDAO.crear(asignatura);
    }

    // --- Métodos para buscar entidades ---
    public Profesor buscarProfesor(int codigo) { // Parámetro 'cod' a 'codigo'
        return profesorDAO.leerPorId(codigo); // Uso de leerPorId
    }

    public Estudiante buscarEstudiante(int codigo) { // Parámetro 'cod' a 'codigo'
        return estudianteDAO.leerPorId(codigo); // Uso de leerPorId
    }

    public Curso buscarCurso(int grado, int grupo) {
        List<Curso> todosLosCursos = cursoDAO.listarTodos();
        for (Curso curso : todosLosCursos) {
            if (curso.obtenerGrado() == grado && curso.obtenerGrupo() == grupo) { // Uso de métodos traducidos
                return curso; 
            }
        }
        return null;
    }
    
    // Sobrecarga para buscar curso por código, que es más directo.
    public Curso buscarCursoPorCodigo(int codigoCurso) {
        return cursoDAO.leerPorId(codigoCurso); // Uso de leerPorId
    }

    public Asignatura buscarAsignatura(String nombre) {
        List<Asignatura> todasLasAsignaturas = asignaturaDAO.listarTodos();
        for (Asignatura asignatura : todasLasAsignaturas) {
            if (asignatura.obtenerNombre().equalsIgnoreCase(nombre)) { // Uso de método traducido
                return asignatura; 
            }
        }
        return null;
    }
    
    // Sobrecarga para buscar asignatura por código.
    public Asignatura buscarAsignaturaPorCodigo(int codigoAsignatura) {
        return asignaturaDAO.leerPorId(codigoAsignatura); // Uso de leerPorId
    }

    // --- Métodos para gestionar relaciones ---
    public boolean agregarCursoAProfesor(int codigoProfesor, int codigoCurso) {
        Profesor profesor = profesorDAO.leerPorId(codigoProfesor); // 'p' a 'profesor', uso de leerPorId
        Curso curso = cursoDAO.leerPorId(codigoCurso); // 'c' a 'curso', uso de leerPorId

        if (profesor != null && curso != null) {
            Curso cursoParaProfesor = new Curso();
            cursoParaProfesor.establecerCodigo(curso.obtenerCodigo()); // Uso de métodos traducidos
            cursoParaProfesor.establecerGrado(curso.obtenerGrado());
            cursoParaProfesor.establecerGrupo(curso.obtenerGrupo());

            Profesor profesorParaCurso = new Profesor();
            profesorParaCurso.establecerCodigo(profesor.obtenerCodigo());
            profesorParaCurso.establecerNombre(profesor.obtenerNombre()); 
            
            profesor.establecerCurso(cursoParaProfesor); 
            curso.establecerProfesor(profesorParaCurso); 

            profesorDAO.actualizar(profesor); 
            cursoDAO.actualizar(curso); 
            return true; 
        } else {
            System.err.println("Colegio: Profesor o Curso no encontrado para agregarCursoAProfesor. Código Profesor: " + codigoProfesor + ", Código Curso: " + codigoCurso);
            return false; 
        }
    }

    public boolean agregarEstudianteACurso(int codigoEstudiante, int codigoCurso) {
        Estudiante estudiante = estudianteDAO.leerPorId(codigoEstudiante); // 'e' a 'estudiante', uso de leerPorId
        Curso curso = cursoDAO.leerPorId(codigoCurso); // 'c' a 'curso', uso de leerPorId

        if (estudiante != null && curso != null) {
            Curso cursoParaEstudiante = new Curso();
            cursoParaEstudiante.establecerCodigo(curso.obtenerCodigo());
            cursoParaEstudiante.establecerGrado(curso.obtenerGrado());
            cursoParaEstudiante.establecerGrupo(curso.obtenerGrupo());

            Estudiante estudianteParaCurso = new Estudiante(); 
            estudianteParaCurso.establecerCodigo(estudiante.obtenerCodigo());

            estudiante.establecerCurso(cursoParaEstudiante); 

            boolean encontrado = false; // 'found' a 'encontrado'
            for (Estudiante estudianteEnCurso : curso.obtenerEstudiantes()) { // 'estEnCurso' a 'estudianteEnCurso', uso de método traducido
                if (estudianteEnCurso.obtenerCodigo() == estudiante.obtenerCodigo()) { // Uso de método traducido
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                curso.obtenerEstudiantes().add(estudianteParaCurso); // Uso de método traducido
            }

            estudianteDAO.actualizar(estudiante); 
            cursoDAO.actualizar(curso); 
            return true;
        } else {
            System.err.println("Colegio: Estudiante o Curso no encontrado para agregarEstudianteACurso. Código Estudiante: " + codigoEstudiante + ", Código Curso: " + codigoCurso);
            return false;
        }
    }

    public boolean agregarAsignaturaAEstudiante(int codigoEstudiante, int codigoAsignatura) {
        Estudiante estudiante = estudianteDAO.leerPorId(codigoEstudiante); // 'e' a 'estudiante', uso de leerPorId
        Asignatura asignatura = asignaturaDAO.leerPorId(codigoAsignatura); // 'a' a 'asignatura', uso de leerPorId

        if (estudiante != null && asignatura != null) {
            Asignatura asignaturaParaEstudiante = new Asignatura();
            asignaturaParaEstudiante.establecerCodigo(asignatura.obtenerCodigo());
            asignaturaParaEstudiante.establecerNombre(asignatura.obtenerNombre()); 

            boolean encontrado = false; // 'found' a 'encontrado'
            for (Asignatura asignaturaEnEstudiante : estudiante.obtenerAsignaturas()) { // 'asigEnEst' a 'asignaturaEnEstudiante', uso de método traducido
                if (asignaturaEnEstudiante.obtenerCodigo() == asignatura.obtenerCodigo()) { // Uso de método traducido
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                estudiante.obtenerAsignaturas().add(asignaturaParaEstudiante); // Uso de método traducido
            }
            
            estudianteDAO.actualizar(estudiante); 
            return true;
        } else {
            System.err.println("Colegio: Estudiante o Asignatura no encontrada para agregarAsignaturaAEstudiante. Código Estudiante: " + codigoEstudiante + ", Código Asignatura: " + codigoAsignatura);
            return false;
        }
    }

    // --- Métodos de reporte (la hidratación puede ser compleja) ---
    public String reporteEstudiante(int codigoEstudiante) {
        Estudiante estudiante = estudianteDAO.leerPorId(codigoEstudiante); // 'e' a 'estudiante', uso de leerPorId
        if (estudiante == null) {
            return "Estudiante no encontrado."; // Mensaje traducido
        }

        // Hidratar Curso para Estudiante
        if (estudiante.obtenerCurso() != null && estudiante.obtenerCurso().obtenerCodigo() != 0) { // Uso de métodos traducidos
            Curso cursoCompleto = cursoDAO.leerPorId(estudiante.obtenerCurso().obtenerCodigo()); // Uso de leerPorId
            if (cursoCompleto != null) {
                estudiante.establecerCurso(cursoCompleto); // Uso de método traducido
            }
        }

        // Hidratar Asignaturas para Estudiante
        if (estudiante.obtenerAsignaturas() != null && !estudiante.obtenerAsignaturas().isEmpty()) { // Uso de método traducido
            ArrayList<Asignatura> asignaturasCompletas = new ArrayList<>();
            for (Asignatura asignaturaMarcadorPosicion : estudiante.obtenerAsignaturas()) { // 'asigPlaceholder' a 'asignaturaMarcadorPosicion'
                if (asignaturaMarcadorPosicion.obtenerCodigo() != 0) { // Uso de método traducido
                    Asignatura asignaturaCompleta = asignaturaDAO.leerPorId(asignaturaMarcadorPosicion.obtenerCodigo()); // Uso de leerPorId
                    if (asignaturaCompleta != null) {
                        asignaturasCompletas.add(asignaturaCompleta);
                    } else {
                        asignaturasCompletas.add(asignaturaMarcadorPosicion); 
                    }
                }
            }
            estudiante.establecerAsignaturas(asignaturasCompletas); // Uso de método traducido
        }
        return estudiante.reporteAcademico(); // El método reporteAcademico ya está en español
    }

    public String infoCurso(int codigoCurso) {
        Curso curso = cursoDAO.leerPorId(codigoCurso); // 'c' a 'curso', uso de leerPorId
        if (curso == null) {
            return "Curso no encontrado."; // Mensaje traducido
        }

        // Hidratar Profesor para Curso
        if (curso.obtenerProfesor() != null && curso.obtenerProfesor().obtenerCodigo() != 0) { // Uso de métodos traducidos
            Profesor profesorCompleto = profesorDAO.leerPorId(curso.obtenerProfesor().obtenerCodigo()); // 'pCompleto' a 'profesorCompleto', uso de leerPorId
            if (profesorCompleto != null) {
                curso.establecerProfesor(profesorCompleto); // Uso de método traducido
            }
        }

        // Hidratar Estudiantes para Curso
        if (curso.obtenerEstudiantes() != null && !curso.obtenerEstudiantes().isEmpty()) { // Uso de método traducido
            ArrayList<Estudiante> estudiantesCompletos = new ArrayList<>();
            for (Estudiante estudianteMarcadorPosicion : curso.obtenerEstudiantes()) { // 'estPlaceholder' a 'estudianteMarcadorPosicion'
                if (estudianteMarcadorPosicion.obtenerCodigo() != 0) { // Uso de método traducido
                    Estudiante estudianteCompleto = estudianteDAO.leerPorId(estudianteMarcadorPosicion.obtenerCodigo()); // 'eCompleto' a 'estudianteCompleto', uso de leerPorId
                    if (estudianteCompleto != null) {
                        if (estudianteCompleto.obtenerCurso() != null && estudianteCompleto.obtenerCurso().obtenerCodigo() != 0) { // Uso de métodos traducidos
                            Curso cursoDeEstudiante = cursoDAO.leerPorId(estudianteCompleto.obtenerCurso().obtenerCodigo()); // Uso de leerPorId
                            estudianteCompleto.establecerCurso(cursoDeEstudiante); 
                        }
                        estudiantesCompletos.add(estudianteCompleto);
                    } else {
                        estudiantesCompletos.add(estudianteMarcadorPosicion); 
                    }
                }
            }
            curso.establecerEstudiantes(estudiantesCompletos); // Uso de método traducido
        }
        return curso.infoCurso(); // El método infoCurso ya está en español
    }
    
    // Sobrecarga por compatibilidad, aunque buscar por código es mejor.
    public String infoCurso(int grado, int grupo) {
        Curso curso = buscarCurso(grado, grupo); // 'c' a 'curso'
         if (curso == null) {
            return "Curso no encontrado."; // Mensaje traducido
        }
        return infoCurso(curso.obtenerCodigo()); // Uso de método traducido
    }

    // Lista todos los cursos con su información detallada.
    public String listarTodosLosCursos() {
        List<Curso> cursos = cursoDAO.listarTodos(); 
        StringBuilder constructorCadena = new StringBuilder(); // 'sb' a 'constructorCadena'
        for (Curso curso : cursos) {
            constructorCadena.append(infoCurso(curso.obtenerCodigo())); // Uso de método traducido
            constructorCadena.append("\n\n");
        }
        return constructorCadena.toString();
    }

    // --- Getters para listas de entidades ---
    public List<Persona> obtenerPersonas() { // Renombrado de getPersonas
        List<Persona> personas = new ArrayList<>();
        personas.addAll(estudianteDAO.listarTodos()); 
        personas.addAll(profesorDAO.listarTodos()); 
        return personas; 
    }

    public List<Curso> obtenerCursos() { // Renombrado de getCursos
        return cursoDAO.listarTodos(); 
    }

    public List<Asignatura> obtenerAsignaturas() { // Renombrado de getAsignaturas
        return asignaturaDAO.listarTodos(); 
    }
    
    // --- Getters y Setters para el nombre del Colegio ---
    public String obtenerNombre() { // Renombrado de getNombre
        return nombre;
    }

    public void establecerNombre(String nombre) { // Renombrado de setNombre
        this.nombre = nombre;
    }

    // Método para MenuDocente: Obtener estudiantes (hidratados) de un curso específico.
    public List<Estudiante> obtenerEstudiantesPorCurso(int codigoCurso) { // Renombrado de getEstudiantesPorCurso
        List<Estudiante> estudiantesDelCurso = new ArrayList<>();
        Curso curso = cursoDAO.leerPorId(codigoCurso); // Uso de leerPorId

        if (curso != null && curso.obtenerEstudiantes() != null) { // Uso de método traducido
            for (Estudiante estudianteMarcadorPosicion : curso.obtenerEstudiantes()) { // 'estPlaceholder' a 'estudianteMarcadorPosicion'
                if (estudianteMarcadorPosicion.obtenerCodigo() != 0) { // Uso de método traducido
                    Estudiante estudianteCompleto = estudianteDAO.leerPorId(estudianteMarcadorPosicion.obtenerCodigo()); // Uso de leerPorId
                    if (estudianteCompleto != null) {
                        if (estudianteCompleto.obtenerCurso() != null && estudianteCompleto.obtenerCurso().obtenerCodigo() != 0) { // Uso de métodos traducidos
                           Curso cursoDelEstudiante = cursoDAO.leerPorId(estudianteCompleto.obtenerCurso().obtenerCodigo()); // Uso de leerPorId
                            estudianteCompleto.establecerCurso(cursoDelEstudiante); // Uso de método traducido
                        }
                        estudiantesDelCurso.add(estudianteCompleto);
                    }
                }
            }
        }
        return estudiantesDelCurso;
    }

    // Método para MenuDocente: Obtener asignaturas (hidratadas) de un estudiante específico.
    public List<Asignatura> obtenerAsignaturasPorEstudiante(int codigoEstudiante) { // Renombrado de getAsignaturasPorEstudiante
        List<Asignatura> asignaturasDelEstudiante = new ArrayList<>();
        Estudiante estudiante = estudianteDAO.leerPorId(codigoEstudiante); // Uso de leerPorId

        if (estudiante != null && estudiante.obtenerAsignaturas() != null) { // Uso de método traducido
            for (Asignatura asignaturaMarcadorPosicion : estudiante.obtenerAsignaturas()) { // 'asigPlaceholder' a 'asignaturaMarcadorPosicion'
                if (asignaturaMarcadorPosicion.obtenerCodigo() != 0) { // Uso de método traducido
                    Asignatura asignaturaCompleta = asignaturaDAO.leerPorId(asignaturaMarcadorPosicion.obtenerCodigo()); // Uso de leerPorId
                    if (asignaturaCompleta != null) {
                        asignaturasDelEstudiante.add(asignaturaCompleta);
                    }
                }
            }
        }
        return asignaturasDelEstudiante;
    }

    // Método para MenuDocente: Agregar una calificación a una asignatura para un estudiante específico.
    public boolean agregarCalificacionAEstudianteAsignatura(int codigoEstudiante, int codigoAsignatura, Calificacion calificacion) {
        // La Calificacion se agrega al objeto Asignatura directamente.
        // La inscripción del Estudiante en esa Asignatura los vincula a estas calificaciones.

        Asignatura asignatura = asignaturaDAO.leerPorId(codigoAsignatura); // Uso de leerPorId
        if (asignatura == null) {
            System.err.println("Colegio: Asignatura con código " + codigoAsignatura + " no encontrada."); // Mensaje traducido
            return false;
        }

        // Validar si el estudiante está realmente inscrito en la asignatura (opcional, pero bueno para la integridad de los datos)
        // Estudiante estudiante = estudianteDAO.leerPorId(codigoEstudiante); 
        // ...

        if (asignatura.obtenerCalificaciones() == null) { // Uso de método traducido
            asignatura.establecerCalificaciones(new ArrayList<>()); // Uso de método traducido
        }
        asignatura.obtenerCalificaciones().add(calificacion); // Uso de método traducido
        
        asignaturaDAO.actualizar(asignatura); 
        System.out.println("Colegio: Calificación agregada a Asignatura " + codigoAsignatura); // Mensaje traducido
        return true;
    }
}
