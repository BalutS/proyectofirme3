package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CursoDAO implements DAO<Curso> { 

    private static final String NOMBRE_ARCHIVO = "cursos.txt"; // Renombrado
    private static final String DELIMITADOR_CSV = ","; // Renombrado
    private static final String DELIMITADOR_LISTA = ";"; // Renombrado

    // Método auxiliar para generar un nuevo ID único para un Curso
    private int generarNuevoIdCurso() { // Renombrado
        List<Curso> cursos = listarTodos(); // Potencialmente ineficiente para archivos muy grandes
        if (cursos.isEmpty()) {
            return 1;
        }
        return cursos.stream()
                .mapToInt(Curso::obtenerCodigo) // Uso de método traducido
                .max()
                .orElse(0) + 1;
    }

    // Método auxiliar para convertir un Curso a una cadena CSV
    private String cursoACadenaCsv(Curso curso) { // Renombrado
        if (curso == null) {
            return "";
        }

        // codigo,grado,grupo,profesor_codigo,estudiante_codigos_delimitados_por_puntoycoma
        String profesorCodigo = (curso.obtenerProfesor() != null) ? String.valueOf(curso.obtenerProfesor().obtenerCodigo()) : "0"; // Uso de métodos traducidos

        String estudianteCodigos = "";
        if (curso.obtenerEstudiantes() != null && !curso.obtenerEstudiantes().isEmpty()) { // Uso de método traducido
            estudianteCodigos = curso.obtenerEstudiantes().stream()
                    .map(est -> String.valueOf(est.obtenerCodigo())) // Uso de método traducido
                    .collect(Collectors.joining(DELIMITADOR_LISTA)); // Uso de constante renombrada
        }

        return String.join(DELIMITADOR_CSV, // Uso de constante renombrada
                String.valueOf(curso.obtenerCodigo()), // Uso de método traducido
                String.valueOf(curso.obtenerGrado()), // Uso de método traducido
                String.valueOf(curso.obtenerGrupo()), // Uso de método traducido
                profesorCodigo,
                estudianteCodigos);
    }

    // Método auxiliar para convertir una cadena CSV a un Curso
    private Curso cadenaCsvACurso(String lineaCsv) { // Renombrado y parámetro traducido
        if (lineaCsv == null || lineaCsv.trim().isEmpty()) {
            return null;
        }
        String[] campos = lineaCsv.split(DELIMITADOR_CSV, -1); // 'fields' a 'campos', uso de constante renombrada

        // Esperado: codigo,grado,grupo,profesor_codigo,estudiante_codigos
        if (campos.length < 5) {
            System.err.println("CursoDAO: Formato de línea CSV inválido: " + lineaCsv); // Mensaje traducido
            return null;
        }

        try {
            int codigo = Integer.parseInt(campos[0]);
            int grado = Integer.parseInt(campos[1]);
            int grupo = Integer.parseInt(campos[2]);
            int profesorCodigo = Integer.parseInt(campos[3]); // Puede ser 0 si no hay profesor
            String estudianteCodigosCadena = campos[4]; // 'estudianteCodigosStr' a 'estudianteCodigosCadena'

            // Crear Profesor marcador de posición
            Profesor profesor = null;
            if (profesorCodigo != 0) {
                profesor = new Profesor(); // Constructor por defecto
                profesor.establecerCodigo(profesorCodigo); // Uso de método traducido
            }

            // Crear Estudiantes marcadores de posición
            ArrayList<Estudiante> listaEstudiantes = new ArrayList<>(); // 'estudiantesList' a 'listaEstudiantes'
            if (!estudianteCodigosCadena.isEmpty()) {
                String[] codigosEstudianteArray = estudianteCodigosCadena.split(DELIMITADOR_LISTA); // 'estudianteCodigosArray' a 'codigosEstudianteArray', uso de constante
                for (String codigoEstudianteCadena : codigosEstudianteArray) { // 'estCodigoStr' a 'codigoEstudianteCadena'
                    if (codigoEstudianteCadena.trim().isEmpty()) continue;
                    Estudiante estudiante = new Estudiante(); // Constructor por defecto
                    estudiante.establecerCodigo(Integer.parseInt(codigoEstudianteCadena.trim())); // Uso de método traducido
                    listaEstudiantes.add(estudiante);
                }
            }
            
            // Usar el constructor: public Curso(int codigo, int grado, int grupo, ArrayList<Estudiante> estudiantes, Profesor profesor)
            return new Curso(codigo, grado, grupo, listaEstudiantes, profesor);

        } catch (NumberFormatException e) {
            System.err.println("CursoDAO: Error parseando número en línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        } catch (Exception e) {
            System.err.println("CursoDAO: Error inesperado parseando línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        }
    }

    @Override
    public void crear(Curso curso) {
        if (curso == null) {
            System.err.println("CursoDAO: No se puede crear un curso nulo."); // Mensaje traducido
            return;
        }
        // Generar nuevo ID si no está ya establecido (ej., codigo es 0 o menos)
        if (curso.obtenerCodigo() <= 0) { // Uso de método traducido
            curso.establecerCodigo(generarNuevoIdCurso()); // Uso de método traducido
        }

        String lineaCsv = cursoACadenaCsv(curso); // 'csvLine' a 'lineaCsv'
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), (lineaCsv + System.lineSeparator()).getBytes(), // Uso de constante renombrada
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("CursoDAO: Error al escribir en el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }

    @Override
    public Curso leerPorId(int id) { // Renombrado de leer
        List<Curso> cursos = listarTodos();
        for (Curso curso : cursos) {
            if (curso.obtenerCodigo() == id) { // Uso de método traducido
                return curso;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Curso cursoActualizado) {
        if (cursoActualizado == null) {
            System.err.println("CursoDAO: No se puede actualizar un curso nulo."); // Mensaje traducido
            return;
        }
        List<Curso> cursos = listarTodos();
        boolean encontrado = false; // 'found' a 'encontrado'
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).obtenerCodigo() == cursoActualizado.obtenerCodigo()) { // Uso de método traducido
                cursos.set(i, cursoActualizado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            reescribirArchivo(cursos);
        } else {
            System.err.println("CursoDAO: Curso con código " + cursoActualizado.obtenerCodigo() + " no encontrado para actualizar."); // Mensaje traducido
        }
    }

    @Override
    public void eliminarPorId(int id) { // Renombrado de eliminar
        List<Curso> cursos = listarTodos();
        boolean eliminado = cursos.removeIf(curso -> curso.obtenerCodigo() == id); // 'removed' a 'eliminado', uso de método traducido

        if (eliminado) {
            reescribirArchivo(cursos);
        } else {
            System.err.println("CursoDAO: Curso con código " + id + " no encontrado para eliminar."); // Mensaje traducido
        }
    }

    @Override
    public List<Curso> listarTodos() {
        List<Curso> cursos = new ArrayList<>();
        if (!Files.exists(Paths.get(NOMBRE_ARCHIVO))) { // Uso de constante renombrada
            return cursos; // Devuelve lista vacía si el archivo no existe
        }
        try {
            List<String> lineas = Files.readAllLines(Paths.get(NOMBRE_ARCHIVO)); // 'lines' a 'lineas', uso de constante
            for (String linea : lineas) { // 'line' a 'linea'
                if (linea.trim().isEmpty()) continue; // Saltar líneas vacías
                Curso curso = cadenaCsvACurso(linea); // Uso de método traducido
                if (curso != null) {
                    cursos.add(curso);
                }
            }
        } catch (IOException e) {
            System.err.println("CursoDAO: Error al leer el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
        return cursos;
    }

    // Método auxiliar para reescribir todo el archivo
    private void reescribirArchivo(List<Curso> cursos) {
        List<String> lineas = cursos.stream() // 'lines' a 'lineas'
                                   .map(this::cursoACadenaCsv) // Uso de método traducido
                                   .filter(Objects::nonNull) // Asegurar que no haya cadenas nulas de la conversión
                                   .collect(Collectors.toList());
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), lineas, // Uso de constante renombrada
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("CursoDAO: Error al reescribir el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }
}
