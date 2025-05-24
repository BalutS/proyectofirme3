package com.modelo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfesorDAO implements DAO<Profesor> { 

    private static final String NOMBRE_ARCHIVO = "profesores.txt"; // Renombrado
    private static final String DELIMITADOR_CSV = ","; // Renombrado

    // Método auxiliar para convertir un Profesor a una cadena CSV
    private String profesorACadenaCsv(Profesor profesor) { // Renombrado
        if (profesor == null) {
            return "";
        }
        // codigo,nombre,edad,cedula,tipo
        String datosPersona = String.join(DELIMITADOR_CSV, // Uso de constante renombrada
                String.valueOf(profesor.obtenerCodigo()), // Uso de método traducido
                profesor.obtenerNombre(), // Uso de método traducido
                String.valueOf(profesor.obtenerEdad()), // Uso de método traducido
                String.valueOf(profesor.obtenerCedula()), // Uso de método traducido
                profesor.obtenerTipo()); // Uso de método traducido

        // curso (grado-grupo)
        String datosCurso = "NULL"; // Valor por defecto si el curso es nulo o sus campos no están definidos
        if (profesor.obtenerCurso() != null) { // Uso de método traducido
            // Comprobar si grado y grupo son valores por defecto (0), podría indicar que no están correctamente definidos.
            // Sin embargo, el requisito es solo almacenar "grado-grupo".
            datosCurso = profesor.obtenerCurso().obtenerGrado() + "-" + profesor.obtenerCurso().obtenerGrupo(); // Uso de métodos traducidos
        }

        return String.join(DELIMITADOR_CSV, datosPersona, datosCurso); // Uso de constante renombrada
    }

    // Método auxiliar para convertir una cadena CSV a un Profesor
    private Profesor cadenaCsvAProfesor(String lineaCsv) { // Renombrado y parámetro traducido
        if (lineaCsv == null || lineaCsv.trim().isEmpty()) {
            return null;
        }
        String[] campos = lineaCsv.split(DELIMITADOR_CSV, -1); // 'fields' a 'campos', uso de constante renombrada

        // Esperado: codigo,nombre,edad,cedula,tipo,cursoCadena
        if (campos.length < 6) {
            System.err.println("ProfesorDAO: Formato de línea CSV inválido: " + lineaCsv); // Mensaje traducido
            return null;
        }

        try {
            int codigo = Integer.parseInt(campos[0]);
            String nombre = campos[1];
            int edad = Integer.parseInt(campos[2]);
            int cedula = Integer.parseInt(campos[3]);
            String tipo = campos[4];
            String cursoCadena = campos[5]; // 'cursoStr' a 'cursoCadena'

            // El constructor Profesor(Curso curso, String nombre, int edad, int cedula, int codigo, String tipo)
            // toma un objeto Curso. Crearemos un Curso marcador de posición primero.
            Curso curso = new Curso(); // Usa constructor por defecto
            if (!"NULL".equals(cursoCadena) && cursoCadena.contains("-")) {
                String[] partesCurso = cursoCadena.split("-"); // 'cursoParts' a 'partesCurso'
                if (partesCurso.length == 2) {
                    try {
                        curso.establecerGrado(Integer.parseInt(partesCurso[0])); // Uso de método traducido
                        curso.establecerGrupo(Integer.parseInt(partesCurso[1])); // Uso de método traducido
                    } catch (NumberFormatException e) {
                        System.err.println("ProfesorDAO: Formato de curso inválido en CSV: " + cursoCadena); // Mensaje traducido
                        // Mantener objeto Curso por defecto
                    }
                }
            }
            
            // Crear Profesor
            Profesor profesor = new Profesor(curso, nombre, edad, cedula, codigo, tipo);
            
            return profesor;

        } catch (NumberFormatException e) {
            System.err.println("ProfesorDAO: Error parseando número en línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        } catch (Exception e) {
            System.err.println("ProfesorDAO: Error inesperado parseando línea CSV: " + lineaCsv + " - " + e.getMessage()); // Mensaje traducido
            return null;
        }
    }

    @Override
    public void crear(Profesor profesor) {
        if (profesor == null) {
            System.err.println("ProfesorDAO: No se puede crear un profesor nulo."); // Mensaje traducido
            return;
        }
        String lineaCsv = profesorACadenaCsv(profesor); // 'csvLine' a 'lineaCsv'
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), (lineaCsv + System.lineSeparator()).getBytes(), // Uso de constante renombrada
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("ProfesorDAO: Error al escribir en el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }

    @Override
    public Profesor leerPorId(int id) { // Renombrado de leer
        List<Profesor> profesores = listarTodos();
        for (Profesor profesor : profesores) {
            if (profesor.obtenerCodigo() == id) { // Uso de método traducido
                return profesor;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Profesor profesorActualizado) {
        if (profesorActualizado == null) {
            System.err.println("ProfesorDAO: No se puede actualizar un profesor nulo."); // Mensaje traducido
            return;
        }
        List<Profesor> profesores = listarTodos();
        boolean encontrado = false; // 'found' a 'encontrado'
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).obtenerCodigo() == profesorActualizado.obtenerCodigo()) { // Uso de método traducido
                profesores.set(i, profesorActualizado);
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            reescribirArchivo(profesores);
        } else {
            System.err.println("ProfesorDAO: Profesor con código " + profesorActualizado.obtenerCodigo() + " no encontrado para actualizar."); // Mensaje traducido
        }
    }

    @Override
    public void eliminarPorId(int id) { // Renombrado de eliminar
        List<Profesor> profesores = listarTodos();
        boolean eliminado = profesores.removeIf(profesor -> profesor.obtenerCodigo() == id); // 'removed' a 'eliminado', uso de método traducido

        if (eliminado) {
            reescribirArchivo(profesores);
        } else {
            System.err.println("ProfesorDAO: Profesor con código " + id + " no encontrado para eliminar."); // Mensaje traducido
        }
    }

    @Override
    public List<Profesor> listarTodos() {
        List<Profesor> profesores = new ArrayList<>();
        if (!Files.exists(Paths.get(NOMBRE_ARCHIVO))) { // Uso de constante renombrada
            return profesores; // Devuelve lista vacía si el archivo no existe
        }
        try {
            List<String> lineas = Files.readAllLines(Paths.get(NOMBRE_ARCHIVO)); // 'lines' a 'lineas', uso de constante
            for (String linea : lineas) { // 'line' a 'linea'
                if (linea.trim().isEmpty()) continue; // Saltar líneas vacías
                Profesor profesor = cadenaCsvAProfesor(linea); // Uso de método traducido
                if (profesor != null) {
                    profesores.add(profesor);
                }
            }
        } catch (IOException e) {
            System.err.println("ProfesorDAO: Error al leer el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
        return profesores;
    }

    // Método auxiliar para reescribir todo el archivo
    private void reescribirArchivo(List<Profesor> profesores) {
        List<String> lineas = profesores.stream() // 'lines' a 'lineas'
                                     .map(this::profesorACadenaCsv) // Uso de método traducido
                                     .collect(Collectors.toList());
        try {
            Files.write(Paths.get(NOMBRE_ARCHIVO), lineas, // Uso de constante renombrada
                    StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("ProfesorDAO: Error al reescribir el archivo " + NOMBRE_ARCHIVO + ": " + e.getMessage()); // Mensaje traducido
        }
    }
}
