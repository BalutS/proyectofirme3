package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CursoDao implements Dao<Curso> {

    private static final String FILE_NAME = "cursos.txt";
    private static final String CSV_DELIMITER = ",";
    private static final String LIST_DELIMITER = ";";

    // Helper to generate a new unique ID for a Curso
    private int generateNewCursoId() {
        List<Curso> cursos = listarTodos(); // Potentially inefficient for very large files
        if (cursos.isEmpty()) {
            return 1;
        }
        return cursos.stream()
                .mapToInt(Curso::getCodigo)
                .max()
                .orElse(0) + 1;
    }

    // Helper method to convert Curso to CSV string
    private String cursoToCsvString(Curso curso) {
        if (curso == null) {
            return "";
        }

        // codigo,grado,grupo,profesor_codigo,estudiante_codigos_semicolon_separated
        String profesorCodigo = (curso.getProfesor() != null) ? String.valueOf(curso.getProfesor().getCodigo()) : "0";

        String estudianteCodigos = "";
        if (curso.getEstudiantes() != null && !curso.getEstudiantes().isEmpty()) {
            estudianteCodigos = curso.getEstudiantes().stream()
                    .map(est -> String.valueOf(est.getCodigo()))
                    .collect(Collectors.joining(LIST_DELIMITER));
        }

        return String.join(CSV_DELIMITER,
                String.valueOf(curso.getCodigo()),
                String.valueOf(curso.getGrado()),
                String.valueOf(curso.getGrupo()),
                profesorCodigo,
                estudianteCodigos);
    }

    // Helper method to convert CSV string to Curso
    private Curso csvStringToCurso(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }
        String[] fields = csvLine.split(CSV_DELIMITER, -1); // -1 to keep trailing empty strings

        // Expected: codigo,grado,grupo,profesor_codigo,estudiante_codigos
        if (fields.length < 5) {
            System.err.println("CursoDao: Invalid CSV line format: " + csvLine);
            return null;
        }

        try {
            int codigo = Integer.parseInt(fields[0]);
            int grado = Integer.parseInt(fields[1]);
            int grupo = Integer.parseInt(fields[2]);
            int profesorCodigo = Integer.parseInt(fields[3]); // Can be 0 if no professor
            String estudianteCodigosStr = fields[4];

            // Create placeholder Profesor
            Profesor profesor = null;
            if (profesorCodigo != 0) {
                profesor = new Profesor(); // Default constructor
                profesor.setCodigo(profesorCodigo); // Set only the ID
            }

            // Create placeholder Estudiantes
            ArrayList<Estudiante> estudiantesList = new ArrayList<>();
            if (!estudianteCodigosStr.isEmpty()) {
                String[] estudianteCodigosArray = estudianteCodigosStr.split(LIST_DELIMITER);
                for (String estCodigoStr : estudianteCodigosArray) {
                    if (estCodigoStr.trim().isEmpty()) continue;
                    Estudiante estudiante = new Estudiante(); // Default constructor
                    estudiante.setCodigo(Integer.parseInt(estCodigoStr.trim())); // Set only the ID
                    estudiantesList.add(estudiante);
                }
            }
            
            // Use the constructor: public Curso(int codigo, int grado, int grupo, ArrayList<Estudiante> estudiantes, Profesor profesor)
            return new Curso(codigo, grado, grupo, estudiantesList, profesor);

        } catch (NumberFormatException e) {
            System.err.println("CursoDao: Error parsing number in CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("CursoDao: Unexpected error parsing CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        }
    }

    @Override
    public void crear(Curso curso) {
        if (curso == null) {
            System.err.println("CursoDao: No se puede crear un curso nulo.");
            return;
        }
        // Generate new ID if not already set (e.g., codigo is 0 or less)
        if (curso.getCodigo() <= 0) {
            curso.setCodigo(generateNewCursoId());
        }

        String csvLine = cursoToCsvString(curso);
        try {
            Files.write(Paths.get(FILE_NAME), (csvLine + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("CursoDao: Error al escribir en el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public Curso leer(int id) {
        List<Curso> cursos = listarTodos();
        for (Curso curso : cursos) {
            if (curso.getCodigo() == id) {
                return curso;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Curso cursoActualizado) {
        if (cursoActualizado == null) {
            System.err.println("CursoDao: No se puede actualizar un curso nulo.");
            return;
        }
        List<Curso> cursos = listarTodos();
        boolean found = false;
        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).getCodigo() == cursoActualizado.getCodigo()) {
                cursos.set(i, cursoActualizado);
                found = true;
                break;
            }
        }

        if (found) {
            reescribirArchivo(cursos);
        } else {
            System.err.println("CursoDao: Curso con código " + cursoActualizado.getCodigo() + " no encontrado para actualizar.");
        }
    }

    @Override
    public void eliminar(int id) {
        List<Curso> cursos = listarTodos();
        boolean removed = cursos.removeIf(curso -> curso.getCodigo() == id);

        if (removed) {
            reescribirArchivo(cursos);
        } else {
            System.err.println("CursoDao: Curso con código " + id + " no encontrado para eliminar.");
        }
    }

    @Override
    public List<Curso> listarTodos() {
        List<Curso> cursos = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_NAME))) {
            return cursos; // Return empty list if file doesn't exist
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                Curso curso = csvStringToCurso(line);
                if (curso != null) {
                    cursos.add(curso);
                }
            }
        } catch (IOException e) {
            System.err.println("CursoDao: Error al leer el archivo " + FILE_NAME + ": " + e.getMessage());
        }
        return cursos;
    }

    // Helper method to rewrite the entire file
    private void reescribirArchivo(List<Curso> cursos) {
        List<String> lines = cursos.stream()
                                   .map(this::cursoToCsvString)
                                   .filter(Objects::nonNull) // Ensure no null strings from conversion
                                   .collect(Collectors.toList());
        try {
            Files.write(Paths.get(FILE_NAME), lines,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("CursoDao: Error al reescribir el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }
}
