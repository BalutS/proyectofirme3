package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstudianteDao implements Dao<Estudiante> {

    private static final String FILE_NAME = "estudiantes.txt";
    private static final String ASIGNATURA_DELIMITER = ";";
    private static final String CSV_DELIMITER = ",";

    // Helper method to convert Estudiante to CSV string
    private String estudianteToCsvString(Estudiante estudiante) {
        if (estudiante == null) {
            return "";
        }
        // codigo,nombre,edad,cedula,tipo
        String personaData = String.join(CSV_DELIMITER,
                String.valueOf(estudiante.getCodigo()),
                estudiante.getNombre(),
                String.valueOf(estudiante.getEdad()),
                String.valueOf(estudiante.getCedula()),
                estudiante.getTipo());

        // curso (grado-grupo)
        String cursoData = "";
        if (estudiante.getCurso() != null) {
            cursoData = estudiante.getCurso().getGrado() + "-" + estudiante.getCurso().getGrupo();
        }

        // asignaturas (nombre1;nombre2;nombre3)
        String asignaturasData = "";
        if (estudiante.getAsignaturas() != null && !estudiante.getAsignaturas().isEmpty()) {
            asignaturasData = estudiante.getAsignaturas().stream()
                    .map(Asignatura::getNombre) // Assumes getNombre is the identifier
                    .collect(Collectors.joining(ASIGNATURA_DELIMITER));
        }

        return String.join(CSV_DELIMITER, personaData, cursoData, asignaturasData);
    }

    // Helper method to convert CSV string to Estudiante
    private Estudiante csvStringToEstudiante(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }
        String[] fields = csvLine.split(CSV_DELIMITER, -1); // -1 to keep trailing empty strings

        // Expected: codigo,nombre,edad,cedula,tipo,cursoStr,asignaturasStr
        if (fields.length < 7) { // Basic validation
            System.err.println("EstudianteDao: Invalid CSV line format: " + csvLine);
            return null;
        }

        try {
            int codigo = Integer.parseInt(fields[0]);
            String nombre = fields[1];
            int edad = Integer.parseInt(fields[2]);
            int cedula = Integer.parseInt(fields[3]);
            String tipo = fields[4];
            String cursoStr = fields[5];
            String asignaturasStr = fields[6];

            // Create Estudiante with Persona fields
            // The constructor Estudiante(ArrayList<Asignatura> asignaturas, String nombre, int edad, int cedula, int codigo, String tipo)
            // initializes asignaturas and curso internally. We will set them afterwards.
            Estudiante estudiante = new Estudiante(new ArrayList<>(), nombre, edad, cedula, codigo, tipo);
            
            // Set Curso (placeholder)
            Curso curso = new Curso(); // Uses default constructor
            if (!cursoStr.isEmpty() && cursoStr.contains("-")) {
                String[] cursoParts = cursoStr.split("-");
                if (cursoParts.length == 2) {
                    try {
                        curso.setGrado(Integer.parseInt(cursoParts[0]));
                        curso.setGrupo(Integer.parseInt(cursoParts[1]));
                    } catch (NumberFormatException e) {
                        System.err.println("EstudianteDao: Invalid curso format in CSV: " + cursoStr);
                        // Keep default Curso object
                    }
                }
            }
            estudiante.setCurso(curso);

            // Set Asignaturas (placeholders)
            ArrayList<Asignatura> asignaturasList = new ArrayList<>();
            if (!asignaturasStr.isEmpty()) {
                String[] asignaturaNombres = asignaturasStr.split(ASIGNATURA_DELIMITER);
                for (String nombreAsignatura : asignaturaNombres) {
                    Asignatura asignatura = new Asignatura(); // Uses default constructor
                    asignatura.setNombre(nombreAsignatura); // Set identifier
                    asignaturasList.add(asignatura);
                }
            }
            estudiante.setAsignaturas(asignaturasList);

            return estudiante;

        } catch (NumberFormatException e) {
            System.err.println("EstudianteDao: Error parsing number in CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        } catch (Exception e) { // Catch any other unexpected errors during parsing
            System.err.println("EstudianteDao: Unexpected error parsing CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        }
    }

    @Override
    public void crear(Estudiante estudiante) {
        if (estudiante == null) {
            System.err.println("EstudianteDao: No se puede crear un estudiante nulo.");
            return;
        }
        String csvLine = estudianteToCsvString(estudiante);
        try {
            Files.write(Paths.get(FILE_NAME), (csvLine + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("EstudianteDao: Error al escribir en el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public Estudiante leer(int id) {
        List<Estudiante> estudiantes = listarTodos();
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCodigo() == id) {
                return estudiante;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Estudiante estudianteActualizado) {
        if (estudianteActualizado == null) {
            System.err.println("EstudianteDao: No se puede actualizar un estudiante nulo.");
            return;
        }
        List<Estudiante> estudiantes = listarTodos();
        boolean found = false;
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getCodigo() == estudianteActualizado.getCodigo()) {
                estudiantes.set(i, estudianteActualizado);
                found = true;
                break;
            }
        }

        if (found) {
            reescribirArchivo(estudiantes);
        } else {
            System.err.println("EstudianteDao: Estudiante con código " + estudianteActualizado.getCodigo() + " no encontrado para actualizar.");
        }
    }

    @Override
    public void eliminar(int id) {
        List<Estudiante> estudiantes = listarTodos();
        boolean removed = estudiantes.removeIf(estudiante -> estudiante.getCodigo() == id);

        if (removed) {
            reescribirArchivo(estudiantes);
        } else {
            System.err.println("EstudianteDao: Estudiante con código " + id + " no encontrado para eliminar.");
        }
    }

    @Override
    public List<Estudiante> listarTodos() {
        List<Estudiante> estudiantes = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_NAME))) {
            return estudiantes; // Return empty list if file doesn't exist
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                Estudiante estudiante = csvStringToEstudiante(line);
                if (estudiante != null) {
                    estudiantes.add(estudiante);
                }
            }
        } catch (IOException e) {
            System.err.println("EstudianteDao: Error al leer el archivo " + FILE_NAME + ": " + e.getMessage());
            // Return potentially partially filled list or empty list on error
        }
        return estudiantes;
    }

    // Helper method to rewrite the entire file
    private void reescribirArchivo(List<Estudiante> estudiantes) {
        List<String> lines = new ArrayList<>();
        for (Estudiante est : estudiantes) {
            lines.add(estudianteToCsvString(est));
        }
        try {
            Files.write(Paths.get(FILE_NAME), lines,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("EstudianteDao: Error al reescribir el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }
}
