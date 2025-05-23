package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfesorDao implements Dao<Profesor> {

    private static final String FILE_NAME = "profesores.txt";
    private static final String CSV_DELIMITER = ",";

    // Helper method to convert Profesor to CSV string
    private String profesorToCsvString(Profesor profesor) {
        if (profesor == null) {
            return "";
        }
        // codigo,nombre,edad,cedula,tipo
        String personaData = String.join(CSV_DELIMITER,
                String.valueOf(profesor.getCodigo()),
                profesor.getNombre(),
                String.valueOf(profesor.getEdad()),
                String.valueOf(profesor.getCedula()),
                profesor.getTipo());

        // curso (grado-grupo)
        String cursoData = "NULL"; // Default if curso is null or fields are not set
        if (profesor.getCurso() != null) {
            // Check if grado and grupo are default (0), might indicate not properly set.
            // However, the requirement is just to store "grado-grupo"
            cursoData = profesor.getCurso().getGrado() + "-" + profesor.getCurso().getGrupo();
        }

        return String.join(CSV_DELIMITER, personaData, cursoData);
    }

    // Helper method to convert CSV string to Profesor
    private Profesor csvStringToProfesor(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }
        String[] fields = csvLine.split(CSV_DELIMITER, -1);

        // Expected: codigo,nombre,edad,cedula,tipo,cursoStr
        if (fields.length < 6) {
            System.err.println("ProfesorDao: Invalid CSV line format: " + csvLine);
            return null;
        }

        try {
            int codigo = Integer.parseInt(fields[0]);
            String nombre = fields[1];
            int edad = Integer.parseInt(fields[2]);
            int cedula = Integer.parseInt(fields[3]);
            String tipo = fields[4];
            String cursoStr = fields[5];

            // The constructor Profesor(Curso curso, String nombre, int edad, int cedula, int codigo, String tipo)
            // takes a Curso object. We will create a placeholder Curso first.
            Curso curso = new Curso(); // Uses default constructor
            if (!"NULL".equals(cursoStr) && cursoStr.contains("-")) {
                String[] cursoParts = cursoStr.split("-");
                if (cursoParts.length == 2) {
                    try {
                        curso.setGrado(Integer.parseInt(cursoParts[0]));
                        curso.setGrupo(Integer.parseInt(cursoParts[1]));
                    } catch (NumberFormatException e) {
                        System.err.println("ProfesorDao: Invalid curso format in CSV: " + cursoStr);
                        // Keep default Curso object
                    }
                }
            }
            
            // Create Profesor
            Profesor profesor = new Profesor(curso, nombre, edad, cedula, codigo, tipo);
            // Or, using default constructor and setters:
            // Profesor profesor = new Profesor();
            // profesor.setCodigo(codigo);
            // profesor.setNombre(nombre);
            // profesor.setEdad(edad);
            // profesor.setCedula(cedula);
            // profesor.setTipo(tipo);
            // profesor.setCurso(curso); // Set the constructed curso

            return profesor;

        } catch (NumberFormatException e) {
            System.err.println("ProfesorDao: Error parsing number in CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("ProfesorDao: Unexpected error parsing CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        }
    }

    @Override
    public void crear(Profesor profesor) {
        if (profesor == null) {
            System.err.println("ProfesorDao: No se puede crear un profesor nulo.");
            return;
        }
        String csvLine = profesorToCsvString(profesor);
        try {
            Files.write(Paths.get(FILE_NAME), (csvLine + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("ProfesorDao: Error al escribir en el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public Profesor leer(int id) {
        List<Profesor> profesores = listarTodos();
        for (Profesor profesor : profesores) {
            if (profesor.getCodigo() == id) {
                return profesor;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Profesor profesorActualizado) {
        if (profesorActualizado == null) {
            System.err.println("ProfesorDao: No se puede actualizar un profesor nulo.");
            return;
        }
        List<Profesor> profesores = listarTodos();
        boolean found = false;
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getCodigo() == profesorActualizado.getCodigo()) {
                profesores.set(i, profesorActualizado);
                found = true;
                break;
            }
        }

        if (found) {
            reescribirArchivo(profesores);
        } else {
            System.err.println("ProfesorDao: Profesor con código " + profesorActualizado.getCodigo() + " no encontrado para actualizar.");
        }
    }

    @Override
    public void eliminar(int id) {
        List<Profesor> profesores = listarTodos();
        boolean removed = profesores.removeIf(profesor -> profesor.getCodigo() == id);

        if (removed) {
            reescribirArchivo(profesores);
        } else {
            System.err.println("ProfesorDao: Profesor con código " + id + " no encontrado para eliminar.");
        }
    }

    @Override
    public List<Profesor> listarTodos() {
        List<Profesor> profesores = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_NAME))) {
            return profesores; // Return empty list if file doesn't exist
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                Profesor profesor = csvStringToProfesor(line);
                if (profesor != null) {
                    profesores.add(profesor);
                }
            }
        } catch (IOException e) {
            System.err.println("ProfesorDao: Error al leer el archivo " + FILE_NAME + ": " + e.getMessage());
        }
        return profesores;
    }

    // Helper method to rewrite the entire file
    private void reescribirArchivo(List<Profesor> profesores) {
        List<String> lines = profesores.stream()
                                     .map(this::profesorToCsvString)
                                     .collect(Collectors.toList());
        try {
            Files.write(Paths.get(FILE_NAME), lines,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("ProfesorDao: Error al reescribir el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }
}
