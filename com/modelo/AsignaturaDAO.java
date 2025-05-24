package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AsignaturaDAO implements DAO<Asignatura> { 

    private static final String NOMBRE_ARCHIVO = "asignaturas.txt"; // Renombrado
    private static final String DELIMITADOR_CSV = ","; // Para campos de Asignatura
    private static final String DELIMITADOR_LISTA_CALIFICACION = "|"; // Para separar cadenas de Calificacion
    // private static final String DELIMITADOR_CAMPO_CALIFICACION = ":"; // Para campos propios de Calificacion (manejado por Calificacion.java)

    // Método auxiliar para generar un nuevo ID único para una Asignatura
    private int generarNuevoIdAsignatura() { // Renombrado
        List<Asignatura> asignaturas = listarTodos();
        if (asignaturas.isEmpty()) {
            return 1;
        }
        return asignaturas.stream()
                .mapToInt(Asignatura::obtenerCodigo) // Uso de método traducido
                .max()
                .orElse(0) + 1;
    }

    // Método auxiliar para convertir List<Calificacion> a una sola cadena
    private String calificacionesACadena(List<Calificacion> calificaciones) { // Renombrado
        if (calificaciones == null || calificaciones.isEmpty()) {
            return "";
        }
        return calificaciones.stream()
                .map(Calificacion::toDaoString) // Usa "nombre:nota:periodo:fecha_iso"
                .collect(Collectors.joining(DELIMITADOR_LISTA_CALIFICACION)); // Uso de constante renombrada
    }

    // Método auxiliar para convertir una sola cadena de vuelta a List<Calificacion>
    private ArrayList<Calificacion> cadenaACalificacionesLista(String calificacionesCadena) { // Renombrado y parámetro traducido
        ArrayList<Calificacion> calificaciones = new ArrayList<>();
        if (calificacionesCadena == null || calificacionesCadena.trim().isEmpty()) {
            return calificaciones;
        }
        // Es necesario escapar el delimitador si es un carácter especial para regex, como '|'
        String[] calificacionesComoCadenas = calificacionesCadena.split("\\" + DELIMITADOR_LISTA_CALIFICACION); // 'calStrings' a 'calificacionesComoCadenas'
        for (String calificacionCadena : calificacionesComoCadenas) { // 'calStr' a 'calificacionCadena'
            if (calificacionCadena.trim().isEmpty()) continue;
            Calificacion cal = Calificacion.fromDaoString(calificacionCadena); // Usa "nombre:nota:periodo:fecha_iso"
            if (cal != null) {
                calificaciones.add(cal);
            }
        }
        return calificaciones;
    }

    // Método auxiliar para convertir Asignatura a una cadena CSV
    private String asignaturaACadenaCsv(Asignatura asignatura) { // Renombrado
        if (asignatura == null) {
            return "";
        }
        // Formato CSV: codigo,nombre,calificaciones_cadena
        String calificacionesCadena = calificacionesACadena(asignatura.obtenerCalificaciones()); // 'calificacionesStr' a 'calificacionesCadena', uso de método traducido
        return String.join(DELIMITADOR_CSV, // Uso de constante renombrada
                String.valueOf(asignatura.obtenerCodigo()), // Uso de método traducido
                asignatura.obtenerNombre(), // Uso de método traducido
                calificacionesCadena);
    }

    // Método auxiliar para convertir una cadena CSV a Asignatura
    private Asignatura cadenaCsvAAsignatura(String lineaCsv) { // Renombrado y parámetro traducido
        if (lineaCsv == null || lineaCsv.trim().isEmpty()) {
            return null;
        }
        String[] campos = lineaCsv.split(DELIMITADOR_CSV, 3); // 'fields' a 'campos', Limita a 3 campos (codigo,nombre,calificaciones_cadena)

        if (campos.length < 3) { // Debe tener al menos codigo, nombre, y (posiblemente vacía) cadena de calificaciones
            System.err.println("AsignaturaDAO: Formato de línea CSV inválido: " + lineaCsv + " (esperados 3 campos)"); // Mensaje traducido
            return null;
        }

        try {
            int codigo = Integer.parseInt(campos[0]);
            String nombre = campos[1];
            String calificacionesCadena = campos[2]; // 'calificacionesStr' a 'calificacionesCadena'

            ArrayList<Calificacion> calificaciones = cadenaACalificacionesLista(calificacionesCadena); // Uso de método traducido
            
            // Usar constructor: public Asignatura(int codigo, String nombre, ArrayList<Calificacion> calificaciones)
            return new Asignatura(codigo, nombre, calificaciones);

        } catch (NumberFormatException e) {
            System.err.println("AsignaturaDAO: Error parseando código en línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        } catch (Exception e) {
            System.err.println("AsignaturaDAO: Error inesperado parseando línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        }
    }

    @Override
    public void crear(Asignatura asignatura) {
        if (asignatura == null) {
            System.err.println("AsignaturaDAO: No se puede crear una asignatura nula."); // Mensaje traducido
            return;
        }
        if (asignatura.obtenerCodigo() <= 0) { // Uso de método traducido
            asignatura.establecerCodigo(generarNuevoIdAsignatura()); // Uso de método traducido
        }

        String lineaCsv = asignaturaACadenaCsv(asignatura); // 'csvLine' a 'lineaCsv'
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), (lineaCsv + System.lineSeparator()).getBytes(), // Uso de constante renombrada
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("AsignaturaDAO: Error al escribir en el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }

    @Override
    public Asignatura leerPorId(int id) { // Renombrado de leer
        List<Asignatura> asignaturas = listarTodos();
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.obtenerCodigo() == id) { // Uso de método traducido
                return asignatura;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Asignatura asignaturaActualizada) {
        if (asignaturaActualizada == null) {
            System.err.println("AsignaturaDAO: No se puede actualizar una asignatura nula."); // Mensaje traducido
            return;
        }
        List<Asignatura> asignaturas = listarTodos();
        boolean encontrado = false; // 'found' a 'encontrado'
        for (int i = 0; i < asignaturas.size(); i++) {
            if (asignaturas.get(i).obtenerCodigo() == asignaturaActualizada.obtenerCodigo()) { // Uso de método traducido
                asignaturas.set(i, asignaturaActualizada);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            reescribirArchivo(asignaturas);
        } else {
            System.err.println("AsignaturaDAO: Asignatura con código " + asignaturaActualizada.obtenerCodigo() + " no encontrada para actualizar."); // Mensaje traducido
        }
    }

    @Override
    public void eliminarPorId(int id) { // Renombrado de eliminar
        List<Asignatura> asignaturas = listarTodos();
        boolean eliminado = asignaturas.removeIf(asignatura -> asignatura.obtenerCodigo() == id); // 'removed' a 'eliminado', uso de método traducido

        if (eliminado) {
            reescribirArchivo(asignaturas);
        } else {
            System.err.println("AsignaturaDAO: Asignatura con código " + id + " no encontrada para eliminar."); // Mensaje traducido
        }
    }

    @Override
    public List<Asignatura> listarTodos() {
        List<Asignatura> asignaturas = new ArrayList<>();
        if (!Files.exists(Paths.get(NOMBRE_ARCHIVO))) { // Uso de constante renombrada
            return asignaturas;
        }
        try {
            List<String> lineas = Files.readAllLines(Paths.get(NOMBRE_ARCHIVO)); // 'lines' a 'lineas', uso de constante
            for (String linea : lineas) { // 'line' a 'linea'
                if (linea.trim().isEmpty()) continue;
                Asignatura asignatura = cadenaCsvAAsignatura(linea); // Uso de método traducido
                if (asignatura != null) {
                    asignaturas.add(asignatura);
                }
            }
        } catch (IOException e) {
            System.err.println("AsignaturaDAO: Error al leer el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
        return asignaturas;
    }

    // Método auxiliar para reescribir todo el archivo
    private void reescribirArchivo(List<Asignatura> asignaturas) {
        List<String> lineas = asignaturas.stream() // 'lines' a 'lineas'
                                   .map(this::asignaturaACadenaCsv) // Uso de método traducido
                                   .filter(Objects::nonNull)
                                   .collect(Collectors.toList());
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), lineas, // Uso de constante renombrada
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("AsignaturaDAO: Error al reescribir el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }
}
