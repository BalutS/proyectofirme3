package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AsignaturaDao implements Dao<Asignatura> {

    private static final String FILE_NAME = "asignaturas.txt";
    private static final String CSV_DELIMITER = ","; // For Asignatura fields
    private static final String CALIFICACION_LIST_DELIMITER = "|"; // To separate Calificacion strings
    private static final String CALIFICACION_FIELD_DELIMITER = ":"; // For Calificacion's own fields (nombre:nota:periodo:fecha)

    // Helper to generate a new unique ID for an Asignatura
    private int generateNewAsignaturaId() {
        List<Asignatura> asignaturas = listarTodos();
        if (asignaturas.isEmpty()) {
            return 1;
        }
        return asignaturas.stream()
                .mapToInt(Asignatura::getCodigo)
                .max()
                .orElse(0) + 1;
    }

    // Helper to convert List<Calificacion> to a single string
    private String calificacionesToString(List<Calificacion> calificaciones) {
        if (calificaciones == null || calificaciones.isEmpty()) {
            return "";
        }
        return calificaciones.stream()
                .map(Calificacion::toDaoString) // Uses "nombre:nota:periodo:fecha_iso"
                .collect(Collectors.joining(CALIFICACION_LIST_DELIMITER));
    }

    // Helper to convert a single string back to List<Calificacion>
    private ArrayList<Calificacion> stringToCalificacionesList(String calificacionesStr) {
        ArrayList<Calificacion> calificaciones = new ArrayList<>();
        if (calificacionesStr == null || calificacionesStr.trim().isEmpty()) {
            return calificaciones;
        }
        String[] calStrings = calificacionesStr.split("\\" + CALIFICACION_LIST_DELIMITER); // Escape if needed
        for (String calStr : calStrings) {
            if (calStr.trim().isEmpty()) continue;
            Calificacion cal = Calificacion.fromDaoString(calStr); // Uses "nombre:nota:periodo:fecha_iso"
            if (cal != null) {
                calificaciones.add(cal);
            }
        }
        return calificaciones;
    }

    // Helper method to convert Asignatura to CSV string
    private String asignaturaToCsvString(Asignatura asignatura) {
        if (asignatura == null) {
            return "";
        }
        // CSV format: codigo,nombre,calificaciones_string
        String calificacionesStr = calificacionesToString(asignatura.getCalificaciones());
        return String.join(CSV_DELIMITER,
                String.valueOf(asignatura.getCodigo()),
                asignatura.getNombre(),
                calificacionesStr);
    }

    // Helper method to convert CSV string to Asignatura
    private Asignatura csvStringToAsignatura(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }
        String[] fields = csvLine.split(CSV_DELIMITER, 3); // Limit to 3 fields (codigo,nombre,calificaciones_str)

        if (fields.length < 3) { // Must have at least codigo, nombre, and (possibly empty) calificaciones string
            System.err.println("AsignaturaDao: Invalid CSV line format: " + csvLine + " (expected 3 fields)");
            return null;
        }

        try {
            int codigo = Integer.parseInt(fields[0]);
            String nombre = fields[1];
            String calificacionesStr = fields[2];

            ArrayList<Calificacion> calificaciones = stringToCalificacionesList(calificacionesStr);
            
            // Use constructor: public Asignatura(int codigo, String nombre, ArrayList<Calificacion> calificaciones)
            return new Asignatura(codigo, nombre, calificaciones);

        } catch (NumberFormatException e) {
            System.err.println("AsignaturaDao: Error parsing codigo in CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("AsignaturaDao: Unexpected error parsing CSV line: " + csvLine + " - " + e.getMessage());
            return null;
        }
    }

    @Override
    public void crear(Asignatura asignatura) {
        if (asignatura == null) {
            System.err.println("AsignaturaDao: No se puede crear una asignatura nula.");
            return;
        }
        if (asignatura.getCodigo() <= 0) {
            asignatura.setCodigo(generateNewAsignaturaId());
        }

        String csvLine = asignaturaToCsvString(asignatura);
        try {
            Files.write(Paths.get(FILE_NAME), (csvLine + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("AsignaturaDao: Error al escribir en el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }

    @Override
    public Asignatura leer(int id) {
        List<Asignatura> asignaturas = listarTodos();
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getCodigo() == id) {
                return asignatura;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Asignatura asignaturaActualizada) {
        if (asignaturaActualizada == null) {
            System.err.println("AsignaturaDao: No se puede actualizar una asignatura nula.");
            return;
        }
        List<Asignatura> asignaturas = listarTodos();
        boolean found = false;
        for (int i = 0; i < asignaturas.size(); i++) {
            if (asignaturas.get(i).getCodigo() == asignaturaActualizada.getCodigo()) {
                asignaturas.set(i, asignaturaActualizada);
                found = true;
                break;
            }
        }

        if (found) {
            reescribirArchivo(asignaturas);
        } else {
            System.err.println("AsignaturaDao: Asignatura con código " + asignaturaActualizada.getCodigo() + " no encontrada para actualizar.");
        }
    }

    @Override
    public void eliminar(int id) {
        List<Asignatura> asignaturas = listarTodos();
        boolean removed = asignaturas.removeIf(asignatura -> asignatura.getCodigo() == id);

        if (removed) {
            reescribirArchivo(asignaturas);
        } else {
            System.err.println("AsignaturaDao: Asignatura con código " + id + " no encontrada para eliminar.");
        }
    }

    @Override
    public List<Asignatura> listarTodos() {
        List<Asignatura> asignaturas = new ArrayList<>();
        if (!Files.exists(Paths.get(FILE_NAME))) {
            return asignaturas;
        }
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                Asignatura asignatura = csvStringToAsignatura(line);
                if (asignatura != null) {
                    asignaturas.add(asignatura);
                }
            }
        } catch (IOException e) {
            System.err.println("AsignaturaDao: Error al leer el archivo " + FILE_NAME + ": " + e.getMessage());
        }
        return asignaturas;
    }

    private void reescribirArchivo(List<Asignatura> asignaturas) {
        List<String> lines = asignaturas.stream()
                                   .map(this::asignaturaToCsvString)
                                   .filter(Objects::nonNull)
                                   .collect(Collectors.toList());
        try {
            Files.write(Paths.get(FILE_NAME), lines,
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("AsignaturaDao: Error al reescribir el archivo " + FILE_NAME + ": " + e.getMessage());
        }
    }
}
