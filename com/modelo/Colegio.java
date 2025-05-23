package com.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Colegio {
    private String nombre;
    private final EstudianteDao estudianteDao;
    private final ProfesorDao profesorDao;
    private final CursoDao cursoDao;
    private final AsignaturaDao asignaturaDao;

    public Colegio(String nombre) {
        this.nombre = nombre;
        this.estudianteDao = new EstudianteDao();
        this.profesorDao = new ProfesorDao();
        this.cursoDao = new CursoDao();
        this.asignaturaDao = new AsignaturaDao();
    }

    // 1. Update agregar<Entity> methods
    public void agregarEstudiante(Estudiante est) {
        if (est == null) return;
        // Ensure Estudiante has Curso and Asignaturas as placeholders if they exist
        // The DAO for Estudiante should handle saving IDs of related entities.
        estudianteDao.crear(est);
    }

    public void agregarProfesor(Profesor prof) {
        if (prof == null) return;
        // Ensure Profesor has Curso as a placeholder if it exists
        profesorDao.crear(prof);
    }

    public void agregarCurso(Curso curso) {
        if (curso == null) return;
        // Ensure Curso has Profesor and Estudiantes as placeholders
        cursoDao.crear(curso);
    }

    public void agregarAsignatura(Asignatura asig) {
        if (asig == null) return;
        // Ensure Asignatura has Calificaciones (if any)
        asignaturaDao.crear(asig);
    }

    // 2. Update buscar<Entity> methods
    public Profesor buscarProfesor(int cod) {
        return profesorDao.leer(cod);
    }

    public Estudiante buscarEstudiante(int cod) {
        return estudianteDao.leer(cod);
    }

    public Curso buscarCurso(int grado, int grupo) {
        List<Curso> todosLosCursos = cursoDao.listarTodos();
        for (Curso curso : todosLosCursos) {
            if (curso.getGrado() == grado && curso.getGrupo() == grupo) {
                return curso; // Returns curso with placeholder relations
            }
        }
        return null;
    }
    
    // Overload buscarCurso by codigo, which is more direct
    public Curso buscarCursoPorCodigo(int codigoCurso) {
        return cursoDao.leer(codigoCurso); // Returns curso with placeholder relations
    }


    public Asignatura buscarAsignatura(String nombre) {
        List<Asignatura> todasLasAsignaturas = asignaturaDao.listarTodos();
        for (Asignatura asignatura : todasLasAsignaturas) {
            if (asignatura.getNombre().equalsIgnoreCase(nombre)) {
                return asignatura; // Returns asignatura with its calificaciones (if DAO populates them)
            }
        }
        return null;
    }
    
    // Overload buscarAsignatura by codigo
    public Asignatura buscarAsignaturaPorCodigo(int codigoAsignatura) {
        return asignaturaDao.leer(codigoAsignatura);
    }

    // 3. Refactor relationship methods
    public boolean agregarCursoAProfesor(int codigoProfesor, int codigoCurso) {
        Profesor p = profesorDao.leer(codigoProfesor);
        Curso c = cursoDao.leer(codigoCurso); 

        if (p != null && c != null) {
            Curso cursoParaProfesor = new Curso();
            cursoParaProfesor.setCodigo(c.getCodigo());
            cursoParaProfesor.setGrado(c.getGrado());
            cursoParaProfesor.setGrupo(c.getGrupo());

            Profesor profesorParaCurso = new Profesor();
            profesorParaCurso.setCodigo(p.getCodigo());
            profesorParaCurso.setNombre(p.getNombre()); 
            
            p.setCurso(cursoParaProfesor); 
            c.setProfesor(profesorParaCurso); 

            // Assuming DAOs' actualizar methods are void and just print errors.
            // If they returned boolean for success, we could check that too.
            profesorDao.actualizar(p);
            cursoDao.actualizar(c);
            return true; // Indicate success
        } else {
            System.err.println("Colegio: Profesor o Curso no encontrado para agregarCursoAProfesor. Código Profesor: " + codigoProfesor + ", Código Curso: " + codigoCurso);
            return false; // Indicate failure: one or both entities not found
        }
    }

    public boolean agregarEstudianteACurso(int codigoEstudiante, int codigoCurso) {
        Estudiante e = estudianteDao.leer(codigoEstudiante);
        Curso c = cursoDao.leer(codigoCurso);

        if (e != null && c != null) {
            // Create placeholders for setting relationships
            Curso cursoParaEstudiante = new Curso();
            cursoParaEstudiante.setCodigo(c.getCodigo());
            cursoParaEstudiante.setGrado(c.getGrado());
            cursoParaEstudiante.setGrupo(c.getGrupo());

            Estudiante estudianteParaCurso = new Estudiante(); // Placeholder with only ID
            estudianteParaCurso.setCodigo(e.getCodigo());
            // Optionally copy other essential fields if Curso.csvStringToCurso needs them for context, but ID is primary.

            e.setCurso(cursoParaEstudiante); // Link placeholder to estudiante

            // Manage Curso's list of Estudiantes
            // The Estudiante objects in c.getEstudiantes() are already placeholders (ID only) from CursoDao
            // We need to ensure the new student is added if not present.
            boolean found = false;
            for (Estudiante estEnCurso : c.getEstudiantes()) {
                if (estEnCurso.getCodigo() == e.getCodigo()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                // Add the placeholder for the new student to the course's list
                c.getEstudiantes().add(estudianteParaCurso);
            }

            estudianteDao.actualizar(e);
            cursoDao.actualizar(c);
            return true;
        } else {
            System.err.println("Colegio: Estudiante o Curso no encontrado para agregarEstudianteACurso. Código Estudiante: " + codigoEstudiante + ", Código Curso: " + codigoCurso);
            return false;
        }
    }

    public boolean agregarAsignaturaAEstudiante(int codigoEstudiante, int codigoAsignatura) {
        Estudiante e = estudianteDao.leer(codigoEstudiante);
        Asignatura a = asignaturaDao.leer(codigoAsignatura);

        if (e != null && a != null) {
            // Create a placeholder for the asignatura to be added to the student
            Asignatura asignaturaParaEstudiante = new Asignatura();
            asignaturaParaEstudiante.setCodigo(a.getCodigo());
            asignaturaParaEstudiante.setNombre(a.getNombre()); // Keep name for context
            // No need to set calificaciones in this placeholder

            // The Asignatura objects in e.getAsignaturas() are already placeholders (ID only) from EstudianteDao
            // We need to ensure the new asignatura is added if not present.
            boolean found = false;
            for (Asignatura asigEnEst : e.getAsignaturas()) {
                if (asigEnEst.getCodigo() == a.getCodigo()) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                 // Add the placeholder for the new asignatura to the student's list
                e.getAsignaturas().add(asignaturaParaEstudiante);
            }
            
            estudianteDao.actualizar(e);
            // No update to Asignatura needed if it doesn't store a list of Estudiantes that are directly linked
            return true;
        } else {
            System.err.println("Colegio: Estudiante o Asignatura no encontrada para agregarAsignaturaAEstudiante. Código Estudiante: " + codigoEstudiante + ", Código Asignatura: " + codigoAsignatura);
            return false;
        }
    }

    // 4. Update reporting methods (Initial pass, hydration will be complex)
    public String reporteEstudiante(int codigoEstudiante) {
        Estudiante e = estudianteDao.leer(codigoEstudiante);
        if (e == null) {
            return "Estudiante no encontrado.";
        }

        // Hydrate Curso for Estudiante
        if (e.getCurso() != null && e.getCurso().getCodigo() != 0) {
            Curso cursoCompleto = cursoDao.leer(e.getCurso().getCodigo());
            if (cursoCompleto != null) {
                 // We need to be careful here. cursoCompleto might have placeholder professor/estudiantes.
                 // For now, let's assume Estudiante.reporteAcademico primarily uses Curso's direct fields (grado, grupo)
                 // or its own (Estudiante's) perspective.
                 // If Curso.toString() is called by reporteAcademico, that might need full hydration of Curso.
                e.setCurso(cursoCompleto); // Set the more complete Curso object.
            }
        }

        // Hydrate Asignaturas for Estudiante
        if (e.getAsignaturas() != null && !e.getAsignaturas().isEmpty()) {
            ArrayList<Asignatura> asignaturasCompletas = new ArrayList<>();
            for (Asignatura asigPlaceholder : e.getAsignaturas()) {
                if (asigPlaceholder.getCodigo() != 0) {
                    Asignatura asignaturaCompleta = asignaturaDao.leer(asigPlaceholder.getCodigo());
                    if (asignaturaCompleta != null) {
                        // AsignaturaDao.leer should return Asignatura with its Calificaciones populated.
                        asignaturasCompletas.add(asignaturaCompleta);
                    } else {
                        asignaturasCompletas.add(asigPlaceholder); // Keep placeholder if full not found
                    }
                }
            }
            e.setAsignaturas(asignaturasCompletas);
        }
        return e.reporteAcademico();
    }

    public String infoCurso(int codigoCurso) {
        Curso c = cursoDao.leer(codigoCurso);
        if (c == null) {
            return "Curso no encontrado.";
        }

        // Hydrate Profesor for Curso
        if (c.getProfesor() != null && c.getProfesor().getCodigo() != 0) {
            Profesor pCompleto = profesorDao.leer(c.getProfesor().getCodigo());
            if (pCompleto != null) {
                 // If pCompleto.getCurso() is used by Curso.infoCurso, it might be a placeholder.
                 // Let's assume Curso.infoCurso focuses on Profesor's direct details.
                c.setProfesor(pCompleto);
            }
        }

        // Hydrate Estudiantes for Curso
        if (c.getEstudiantes() != null && !c.getEstudiantes().isEmpty()) {
            ArrayList<Estudiante> estudiantesCompletos = new ArrayList<>();
            for (Estudiante estPlaceholder : c.getEstudiantes()) {
                if (estPlaceholder.getCodigo() != 0) {
                    Estudiante eCompleto = estudianteDao.leer(estPlaceholder.getCodigo());
                    if (eCompleto != null) {
                        // Hydrate Curso for this Estudiante within Curso's list (if needed by Estudiante.toString called by Curso.infoCurso)
                        if (eCompleto.getCurso() != null && eCompleto.getCurso().getCodigo() != 0) {
                            Curso cursoDeEstudiante = cursoDao.leer(eCompleto.getCurso().getCodigo());
                            eCompleto.setCurso(cursoDeEstudiante); // Set potentially more complete Curso
                        }
                        // Asignaturas for Estudiante usually not needed for basic Curso.infoCurso display
                        estudiantesCompletos.add(eCompleto);
                    } else {
                        estudiantesCompletos.add(estPlaceholder); // Keep placeholder
                    }
                }
            }
            c.setEstudiantes(estudiantesCompletos);
        }
        return c.infoCurso();
    }
    
    // Overload for previous signature, if necessary, but codigoCurso is better
    public String infoCurso(int grado, int grupo) {
        Curso c = buscarCurso(grado, grupo); // This finds by iterating, returns with placeholders
         if (c == null) {
            return "Curso no encontrado.";
        }
        return infoCurso(c.getCodigo()); // Call the codigo-based one for hydration
    }


    public String listarTodosLosCursos() {
        List<Curso> cursos = cursoDao.listarTodos(); // Gets courses with placeholder relations
        StringBuilder sb = new StringBuilder();
        for (Curso curso : cursos) {
            // Call the hydrating infoCurso method for each course
            sb.append(infoCurso(curso.getCodigo())); 
            sb.append("\n\n");
        }
        return sb.toString();
    }

    // 5. Getters for lists
    public List<Persona> getPersonas() {
        List<Persona> personas = new ArrayList<>();
        personas.addAll(estudianteDao.listarTodos());
        personas.addAll(profesorDao.listarTodos());
        return personas; // These will be objects with placeholder relations
    }

    public List<Curso> getCursos() {
        return cursoDao.listarTodos(); // Objects with placeholder relations
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturaDao.listarTodos(); // Objects with their calificaciones (as per AsignaturaDao)
    }
    
    // Getters and Setters for nombre
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // New method for MenuDocente
    public List<Estudiante> getEstudiantesPorCurso(int codigoCurso) {
        List<Estudiante> estudiantesDelCurso = new ArrayList<>();
        Curso curso = cursoDao.leer(codigoCurso); // Gets Curso with placeholder Estudiante list

        if (curso != null && curso.getEstudiantes() != null) {
            for (Estudiante estPlaceholder : curso.getEstudiantes()) {
                if (estPlaceholder.getCodigo() != 0) {
                    Estudiante estudianteCompleto = estudianteDao.leer(estPlaceholder.getCodigo());
                    if (estudianteCompleto != null) {
                        // Hydrate curso for this estudiante as well, if needed for its toString or other methods
                        if (estudianteCompleto.getCurso() != null && estudianteCompleto.getCurso().getCodigo() != 0) {
                           Curso cursoDelEstudiante = cursoDao.leer(estudianteCompleto.getCurso().getCodigo());
                            estudianteCompleto.setCurso(cursoDelEstudiante);
                        }
                        // Optionally hydrate asignaturas if they are to be displayed in the list
                        // For now, Estudiante.toString() is basic for this context.
                        estudiantesDelCurso.add(estudianteCompleto);
                    }
                }
            }
        }
        return estudiantesDelCurso;
    }

    // New method for MenuDocente: Get (hydrated) asignaturas for a specific student
    public List<Asignatura> getAsignaturasPorEstudiante(int codigoEstudiante) {
        List<Asignatura> asignaturasDelEstudiante = new ArrayList<>();
        Estudiante estudiante = estudianteDao.leer(codigoEstudiante); // Gets Estudiante with placeholder Asignatura list

        if (estudiante != null && estudiante.getAsignaturas() != null) {
            for (Asignatura asigPlaceholder : estudiante.getAsignaturas()) {
                if (asigPlaceholder.getCodigo() != 0) {
                    // Fetch the full Asignatura object, which includes its list of Calificaciones
                    Asignatura asignaturaCompleta = asignaturaDao.leer(asigPlaceholder.getCodigo());
                    if (asignaturaCompleta != null) {
                        asignaturasDelEstudiante.add(asignaturaCompleta);
                    }
                }
            }
        }
        return asignaturasDelEstudiante;
    }

    // New method for MenuDocente: Add a calificacion to an asignatura for a specific student
    public boolean agregarCalificacionAEstudianteAsignatura(int codigoEstudiante, int codigoAsignatura, Calificacion calificacion) {
        // The Calificacion is added to the Asignatura object itself.
        // The Estudiante's enrollment in that Asignatura links them to these grades.

        Asignatura asignatura = asignaturaDao.leer(codigoAsignatura);
        if (asignatura == null) {
            System.err.println("Colegio: Asignatura con código " + codigoAsignatura + " no encontrada.");
            return false;
        }

        // Validate if student is actually enrolled in the asignatura (optional, but good for data integrity)
        // This step is implicitly handled if the UI only shows asignaturas the student is enrolled in.
        // Estudiante estudiante = estudianteDao.leer(codigoEstudiante);
        // boolean enrolled = false;
        // if (estudiante != null && estudiante.getAsignaturas() != null) {
        //     for (Asignatura asigEst : estudiante.getAsignaturas()) {
        //         if (asigEst.getCodigo() == codigoAsignatura) {
        //             enrolled = true;
        //             break;
        //         }
        //     }
        // }
        // if (!enrolled) {
        //     System.err.println("Colegio: Estudiante " + codigoEstudiante + " no parece estar en la asignatura " + codigoAsignatura);
        //     return false; // Or handle as per application rules
        // }


        // Add calificacion to the main Asignatura object's list of calificaciones
        if (asignatura.getCalificaciones() == null) {
            asignatura.setCalificaciones(new ArrayList<>());
        }
        asignatura.getCalificaciones().add(calificacion);
        
        // Update the Asignatura in the file
        asignaturaDao.actualizar(asignatura);
        System.out.println("Colegio: Calificación agregada a Asignatura " + codigoAsignatura);
        return true;
    }
}
